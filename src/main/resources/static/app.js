const DATABASE_NAME = "nexo-creator-desk";
const STORE_NAME = "models";
const MAX_IMAGE_BYTES = 8 * 1024 * 1024;
const MAX_IMAGE_EDGE = 720;

const elements = {
  modelList: document.querySelector("#model-list"),
  commissionList: document.querySelector("#commission-list"),
  emptyState: document.querySelector("#empty-state"),
  commissionEmpty: document.querySelector("#commission-empty"),
  modelSearch: document.querySelector("#model-search"),
  modelDialog: document.querySelector("#model-dialog"),
  deleteDialog: document.querySelector("#delete-dialog"),
  modelForm: document.querySelector("#model-form"),
  photoInput: document.querySelector("#model-photo"),
  photoPreview: document.querySelector("#photo-preview"),
  formError: document.querySelector("#form-error"),
  toast: document.querySelector("#toast"),
};

let models = [];
let editingId = null;
let deletingId = null;
let pendingPhoto = "";
let toastTimeout;

function openDatabase() {
  return new Promise((resolve, reject) => {
    const request = indexedDB.open(DATABASE_NAME, 1);
    request.onupgradeneeded = () => {
      if (!request.result.objectStoreNames.contains(STORE_NAME)) {
        request.result.createObjectStore(STORE_NAME, { keyPath: "id" });
      }
    };
    request.onsuccess = () => resolve(request.result);
    request.onerror = () => reject(request.error);
  });
}

async function storeRequest(mode, operation) {
  const database = await openDatabase();
  return new Promise((resolve, reject) => {
    const transaction = database.transaction(STORE_NAME, mode);
    const request = operation(transaction.objectStore(STORE_NAME));
    let result;
    request.onsuccess = () => { result = request.result; };
    request.onerror = () => reject(request.error);
    transaction.oncomplete = () => {
      database.close();
      resolve(result);
    };
    transaction.onerror = () => reject(transaction.error);
    transaction.onabort = () => reject(transaction.error);
  });
}

const readModels = () => storeRequest("readonly", store => store.getAll());
const saveModel = model => storeRequest("readwrite", store => store.put(model));
const removeModel = id => storeRequest("readwrite", store => store.delete(id));

function escapeHtml(value = "") {
  return String(value).replace(/[&<>"']/g, character => ({
    "&": "&amp;",
    "<": "&lt;",
    ">": "&gt;",
    '"': "&quot;",
    "'": "&#39;",
  })[character]);
}

function formatCurrency(value) {
  return new Intl.NumberFormat("es-AR", {
    style: "currency",
    currency: "ARS",
    maximumFractionDigits: 2,
  }).format(Number(value) || 0);
}

function commissionFor(model) {
  return Number(model.revenue) * Number(model.rate) / 100;
}

function initials(name) {
  return name.trim().slice(0, 1).toLocaleUpperCase("es");
}

function showToast(message) {
  elements.toast.textContent = message;
  elements.toast.classList.add("visible");
  window.clearTimeout(toastTimeout);
  toastTimeout = window.setTimeout(() => elements.toast.classList.remove("visible"), 2800);
}

function render() {
  const query = elements.modelSearch.value.trim().toLocaleLowerCase("es");
  const filtered = models.filter(model => `${model.name} ${model.handle}`.toLocaleLowerCase("es").includes(query));
  const totalRevenue = models.reduce((total, model) => total + Number(model.revenue), 0);
  const totalCommission = models.reduce((total, model) => total + commissionFor(model), 0);

  document.querySelector("#stat-models").textContent = models.length;
  document.querySelector("#nav-count").textContent = models.length;
  document.querySelector("#heading-count").textContent = models.length;
  document.querySelector("#stat-revenue").textContent = formatCurrency(totalRevenue);
  document.querySelector("#stat-commission").textContent = formatCurrency(totalCommission);
  document.querySelector("#commission-total").textContent = formatCurrency(totalCommission);
  elements.emptyState.hidden = models.length > 0;
  elements.modelList.hidden = models.length === 0;

  if (models.length > 0 && filtered.length === 0) {
    elements.modelList.innerHTML = '<p class="commission-empty">No encontramos modelos con ese nombre.</p>';
  } else {
    elements.modelList.innerHTML = filtered.map(model => `
      <article class="model-row">
        <div class="model-identity">
          <span class="model-photo">${model.photo ? `<img src="${model.photo}" alt="">` : escapeHtml(initials(model.name))}</span>
          <span class="model-identity-text">
            <span class="model-name">${escapeHtml(model.name)}</span>
            <span class="model-handle">${model.handle ? `@${escapeHtml(model.handle)}` : "Sin usuario"}</span>
          </span>
        </div>
        <div class="model-metric"><span>Ingresos</span><strong>${formatCurrency(model.revenue)}</strong></div>
        <div class="model-metric"><span>Tu comisión · ${escapeHtml(model.rate)}%</span><strong class="metric-green">${formatCurrency(commissionFor(model))}</strong></div>
        <div class="row-actions">
          <button class="icon-button menu-button" type="button" data-id="${escapeHtml(model.id)}" aria-label="Opciones de ${escapeHtml(model.name)}" aria-expanded="false">···</button>
        </div>
      </article>
    `).join("");
  }

  elements.commissionEmpty.hidden = models.length > 0;
  elements.commissionList.innerHTML = models
    .slice()
    .sort((first, second) => commissionFor(second) - commissionFor(first))
    .map(model => `
      <div class="commission-item">
        <div><strong>${escapeHtml(model.name)}</strong><span>${escapeHtml(model.rate)}% de ${formatCurrency(model.revenue)}</span></div>
        <strong class="commission-value">${formatCurrency(commissionFor(model))}</strong>
      </div>
    `).join("");
}

function openForm(model = null) {
  editingId = model?.id ?? null;
  pendingPhoto = model?.photo ?? "";
  elements.modelForm.reset();
  elements.formError.textContent = "";
  document.querySelector("#dialog-title").textContent = model ? "Editar modelo" : "Agregar modelo";
  document.querySelector("#save-model").textContent = model ? "Guardar cambios" : "Guardar modelo";
  document.querySelector("#model-name").value = model?.name ?? "";
  document.querySelector("#model-handle").value = model?.handle ?? "";
  document.querySelector("#model-revenue").value = model?.revenue ?? "";
  document.querySelector("#model-rate").value = model?.rate ?? "";
  document.querySelector("#model-adult").checked = model?.adultConfirmed ?? false;
  document.querySelector("#model-consent").checked = model?.photoConsent ?? false;
  updatePhotoPreview();
  updateCommissionPreview();
  elements.modelDialog.showModal();
  window.setTimeout(() => document.querySelector("#model-name").focus(), 0);
}

function updatePhotoPreview() {
  elements.photoPreview.innerHTML = pendingPhoto
    ? `<img src="${pendingPhoto}" alt="Vista previa de la foto">`
    : '<svg viewBox="0 0 24 24" aria-hidden="true"><rect x="3" y="4" width="18" height="16" rx="3"/><circle cx="9" cy="10" r="2"/><path d="m4 17 5-4 3 2 3-3 5 5"/></svg>';
}

function updateCommissionPreview() {
  const revenue = Number(document.querySelector("#model-revenue").value) || 0;
  const rate = Number(document.querySelector("#model-rate").value) || 0;
  document.querySelector("#form-commission").textContent = formatCurrency(revenue * rate / 100);
}

function processPhoto(file) {
  return new Promise((resolve, reject) => {
    if (!file.type.startsWith("image/") || !["image/jpeg", "image/png", "image/webp"].includes(file.type)) {
      reject(new Error("Elegí una imagen JPG, PNG o WebP."));
      return;
    }
    if (file.size > MAX_IMAGE_BYTES) {
      reject(new Error("La foto debe pesar menos de 8 MB."));
      return;
    }
    const image = new Image();
    const objectUrl = URL.createObjectURL(file);
    image.onload = () => {
      const scale = Math.min(1, MAX_IMAGE_EDGE / Math.max(image.naturalWidth, image.naturalHeight));
      const canvas = document.createElement("canvas");
      canvas.width = Math.max(1, Math.round(image.naturalWidth * scale));
      canvas.height = Math.max(1, Math.round(image.naturalHeight * scale));
      canvas.getContext("2d").drawImage(image, 0, 0, canvas.width, canvas.height);
      URL.revokeObjectURL(objectUrl);
      resolve(canvas.toDataURL("image/jpeg", 0.78));
    };
    image.onerror = () => {
      URL.revokeObjectURL(objectUrl);
      reject(new Error("No pudimos leer esa imagen. Probá con otro archivo."));
    };
    image.src = objectUrl;
  });
}

function validNumber(value, min, max = Number.POSITIVE_INFINITY) {
  return Number.isFinite(value) && value >= min && value <= max;
}

async function refreshModels() {
  models = (await readModels()).sort((first, second) => first.name.localeCompare(second.name, "es"));
  render();
}

document.querySelectorAll("#add-model-top, #add-model-main, #add-model-empty").forEach(button => {
  button.addEventListener("click", () => openForm());
});
document.querySelector("#close-dialog").addEventListener("click", () => elements.modelDialog.close());
document.querySelector("#cancel-dialog").addEventListener("click", () => elements.modelDialog.close());
document.querySelector("#keep-model").addEventListener("click", () => elements.deleteDialog.close());
elements.modelSearch.addEventListener("input", render);

["#model-revenue", "#model-rate"].forEach(selector => {
  document.querySelector(selector).addEventListener("input", updateCommissionPreview);
});

elements.photoInput.addEventListener("change", async event => {
  const [file] = event.target.files;
  if (!file) return;
  elements.formError.textContent = "";
  try {
    pendingPhoto = await processPhoto(file);
    updatePhotoPreview();
  } catch (error) {
    elements.formError.textContent = error.message;
    elements.photoInput.value = "";
  }
});

elements.modelForm.addEventListener("submit", async event => {
  event.preventDefault();
  const name = document.querySelector("#model-name").value.trim();
  const handle = document.querySelector("#model-handle").value.trim().replace(/^@+/, "");
  const revenueInput = document.querySelector("#model-revenue").value.trim();
  const rateInput = document.querySelector("#model-rate").value.trim();
  const revenue = Number(revenueInput);
  const rate = Number(rateInput);
  const adultConfirmed = document.querySelector("#model-adult").checked;
  const photoConsent = document.querySelector("#model-consent").checked;

  if (!name) {
    elements.formError.textContent = "Ingresá el nombre o alias de la modelo.";
    return;
  }
  if (!revenueInput || !validNumber(revenue, 0)) {
    elements.formError.textContent = "Ingresá un ingreso válido, igual o mayor a cero.";
    return;
  }
  if (!rateInput || !validNumber(rate, 0, 100)) {
    elements.formError.textContent = "La comisión debe estar entre 0% y 100%.";
    return;
  }
  if (!adultConfirmed || !photoConsent) {
    elements.formError.textContent = "Confirmá la mayoría de edad y la autorización para guardar la foto.";
    return;
  }

  const existing = models.find(model => model.id === editingId);
  const model = {
    id: editingId ?? crypto.randomUUID(),
    name,
    handle,
    revenue,
    rate,
    photo: pendingPhoto,
    adultConfirmed,
    photoConsent,
    createdAt: existing?.createdAt ?? new Date().toISOString(),
    updatedAt: new Date().toISOString(),
  };
  try {
    await saveModel(model);
    await refreshModels();
    elements.modelDialog.close();
    showToast(editingId ? "Perfil actualizado." : "Modelo agregada a tu equipo.");
  } catch (error) {
    console.error("No se pudo guardar el perfil.", error);
    elements.formError.textContent = "No se pudo guardar. Revisá el espacio disponible en el navegador e intentá de nuevo.";
  }
});

elements.modelList.addEventListener("click", event => {
  const button = event.target.closest(".menu-button");
  if (!button) return;
  const existingMenu = button.parentElement.querySelector(".row-menu");
  if (existingMenu) {
    existingMenu.remove();
    button.setAttribute("aria-expanded", "false");
    return;
  }
  elements.modelList.querySelectorAll(".row-menu").forEach(menu => menu.remove());
  elements.modelList.querySelectorAll(".menu-button").forEach(item => item.setAttribute("aria-expanded", "false"));
  button.setAttribute("aria-expanded", "true");
  const menu = document.createElement("div");
  menu.className = "row-menu";
  menu.innerHTML = '<button type="button" data-action="edit">Editar perfil</button><button type="button" data-action="delete">Eliminar perfil</button>';
  button.parentElement.append(menu);
});

elements.modelList.addEventListener("click", event => {
  const action = event.target.closest("[data-action]");
  if (!action) return;
  const row = action.closest(".model-row");
  const id = row?.querySelector(".menu-button")?.dataset.id;
  if (!id) return;
  const model = models.find(item => item.id === id);
  if (action.dataset.action === "edit" && model) openForm(model);
  if (action.dataset.action === "delete") {
    deletingId = id;
    elements.deleteDialog.showModal();
  }
});

document.querySelector("#confirm-delete").addEventListener("click", async () => {
  if (!deletingId) return;
  try {
    await removeModel(deletingId);
    await refreshModels();
    elements.deleteDialog.close();
    showToast("Perfil eliminado.");
  } catch (error) {
    console.error("No se pudo eliminar el perfil.", error);
    showToast("No se pudo eliminar. Intentá nuevamente.");
  } finally {
    deletingId = null;
  }
});

document.querySelector("#today-label").textContent = new Intl.DateTimeFormat("es-AR", {
  weekday: "short",
  day: "numeric",
  month: "short",
}).format(new Date());

refreshModels().catch(error => {
  console.error("No se pudo abrir la base local.", error);
  showToast("No se pudo acceder al almacenamiento de este navegador.");
});
