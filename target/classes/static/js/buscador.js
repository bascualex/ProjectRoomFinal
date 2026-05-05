document.addEventListener('DOMContentLoaded', function() {
    const inputBusqueda = document.getElementById('buscador-proyectos');
    const contenedorProyectos = document.getElementById('grid-proyectos');

    if (inputBusqueda && contenedorProyectos) {
        inputBusqueda.addEventListener('input', function(e) {
            const query = e.target.value.toLowerCase().trim();
            const tarjetas = contenedorProyectos.querySelectorAll('.proyecto-card');

            tarjetas.forEach(tarjeta => {
                // Obtenemos el nombre del proyecto del h3 dentro de la tarjeta
                const nombreProyecto = tarjeta.querySelector('h3').textContent.toLowerCase();

                // Si el nombre contiene lo que escribimos, mostramos la tarjeta, si no, la ocultamos
                if (nombreProyecto.includes(query)) {
                    tarjeta.style.display = 'flex';
                } else {
                    tarjeta.style.display = 'none';
                }
            });
        });
    }
});