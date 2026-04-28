/* Inicializar calendarios Flatpickr */

// Campo fecha estreno
flatpickr("#inp-fechaEstreno", {
    dateFormat: "Y-m-d",   // Formato año-mes-día
    allowInput: true       // Permite escribir manualmente
});

// Campo fin cartelera
flatpickr("#inp-fechaFinCartelera", {
    dateFormat: "Y-m-d",
    allowInput: true
});


/* Cerrar alerta automáticamente después de 4 segundos */

setTimeout(() => {

    // Buscar alerta flotante
    const alerta = document.querySelector(".alert-flotante");

    // Si existe, cerrarla con Bootstrap
    if (alerta) {
        const bsAlert = bootstrap.Alert.getOrCreateInstance(alerta);
        bsAlert.close();
    }

}, 4000);


/* Función para actualizar vista previa  */

function aplicarPreview() {

    // Función corta para obtener valor por id
    const get = (id) => document.getElementById(id)?.value || "";

    // Cambiar título
    document.getElementById("prev-titulo").textContent =
        get("inp-titulo") || "TÍTULO";

    // Cambiar género
    document.getElementById("prev-genero").textContent =
        get("inp-genero") || "Género";

    // Cambiar duración
    document.getElementById("prev-duracion").textContent =
        (get("inp-duracion") || "0") + " min";

    // Cambiar formato
    document.getElementById("prev-formato").textContent =
        get("inp-formato") || "Formato";

    // Cambiar idioma
    document.getElementById("prev-idioma").textContent =
        get("inp-idioma") || "Idioma";

    // Cambiar clasificación
    document.getElementById("prev-clasificacion").textContent =
        get("inp-clasificacion") || "+12";

    // Cambiar imagen
    const imgUrl = get("inp-imagen");

    document.getElementById("prev-imagen").src =
        imgUrl || "/Images/Movie-Icon.png";
}



/* Al cargar la página, editando, mostrar preview inicial */
window.addEventListener("DOMContentLoaded", () => {

    // Si existe título con contenido, aplicar preview
    if (document.getElementById("inp-titulo")?.value) {
        aplicarPreview();
    }

});