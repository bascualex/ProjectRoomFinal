const MESES = ['Ene','Feb','Mar','Abr','May','Jun','Jul','Ago','Sep','Oct','Nov','Dic'];

document.addEventListener('DOMContentLoaded', function () {
    document.getElementById('fechaInicial').addEventListener('input', calcularCuatrimestres);
    document.getElementById('fechaFinal').addEventListener('input', calcularCuatrimestres);
    document.getElementById('presupuesto').addEventListener('input', calcularCuatrimestres);
});

function calcularCuatrimestres() {
    const inicio    = document.getElementById('fechaInicial').value;
    const fin       = document.getElementById('fechaFinal').value;
    const presup    = parseInt(document.getElementById('presupuesto').value) || 0;
    const container = document.getElementById('cuatrimestres-container');
    const grid      = document.getElementById('cuatrimestres-grid');

    if (!inicio || !fin || presup <= 0) {
        container.classList.add('hidden');
        grid.innerHTML = '';
        return;
    }

    const dInicio = new Date(inicio + 'T00:00:00');
    const dFin    = new Date(fin    + 'T00:00:00');

    if (dFin <= dInicio) {
        container.classList.add('hidden');
        grid.innerHTML = '';
        return;
    }

    // Bloques de 4 meses (cuatrimestre real)
    const cuatrimestres = [];
    const cursor = new Date(dInicio);

    while (cursor <= dFin) {
        const bloque = [];
        for (let i = 0; i < 4; i++) {
            if (cursor > dFin) break;
            bloque.push(new Date(cursor));
            cursor.setMonth(cursor.getMonth() + 1);
        }
        cuatrimestres.push(bloque);
    }

    const numC      = cuatrimestres.length;
    const porcBase  = Math.floor(100 / numC);
    const restoPorc = 100 - porcBase * numC;

    document.getElementById('presupuesto-total-label').textContent =
        '$ ' + presup.toLocaleString('es-ES');

    grid.innerHTML = cuatrimestres.map((bloque, i) => {
        const desde       = bloque[0];
        const hasta       = bloque[bloque.length - 1];
        const label       = MESES[desde.getMonth()] + ' ' + desde.getFullYear()
                          + ' – '
                          + MESES[hasta.getMonth()] + ' ' + hasta.getFullYear();
        const mesesBloque = bloque.length;
        const porcentaje  = porcBase + (i === numC - 1 ? restoPorc : 0);

        return `
        <div class="bg-dark-800 border border-slate-700 rounded-xl p-4 space-y-3">
            <div class="flex items-center justify-between">
                <div class="flex items-center gap-2">
                    <span class="w-6 h-6 bg-blue-500/20 text-blue-400 rounded-md flex items-center justify-center text-xs font-bold">
                        ${i + 1}
                    </span>
                    <span class="text-xs font-semibold text-slate-300">${label}</span>
                </div>
                <span class="text-xs text-slate-500">${mesesBloque} ${mesesBloque === 1 ? 'mes' : 'meses'}</span>
            </div>

            <div class="space-y-1.5">
                <div class="flex items-center justify-between">
                    <span class="text-xs text-slate-400">% del presupuesto planificado</span>
                    <span id="label-porc-${i}" class="text-xs font-semibold text-white">${porcentaje}%</span>
                </div>
                <div class="relative">
                    <input
                        type="range"
                        name="porcentajes"
                        value="${porcentaje}"
                        min="0"
                        max="100"
                        step="1"
                        oninput="actualizarPorcentaje(${i}, this.value)"
                        class="w-full h-2 rounded-full appearance-none cursor-pointer accent-blue-500 bg-slate-700"
                    >
                </div>
                <div class="flex justify-between text-xs text-slate-500">
                    <span>$ 0</span>
                    <span id="label-monto-${i}" class="text-blue-400 font-medium">
                        $ ${Math.round(presup * porcentaje / 100).toLocaleString('es-ES')}
                    </span>
                    <span>$ ${presup.toLocaleString('es-ES')}</span>
                </div>
            </div>
        </div>`;
    }).join('');

    container.classList.remove('hidden');
    validarSuma();
}

function actualizarPorcentaje(index, valor) {
    const presup = parseInt(document.getElementById('presupuesto').value) || 0;
    const monto  = Math.round(presup * valor / 100);
    document.getElementById('label-porc-'  + index).textContent = valor + '%';
    document.getElementById('label-monto-' + index).textContent = '$ ' + monto.toLocaleString('es-ES');
    validarSuma();
}

function validarSuma() {
    const inputs  = document.querySelectorAll('input[name="porcentajes"]');
    const alerta  = document.getElementById('alerta-suma');
    const texto   = document.getElementById('alerta-suma-texto');

    let suma = 0;
    inputs.forEach(inp => suma += parseInt(inp.value) || 0);

    const diff = suma - 100;

    if (diff !== 0 && inputs.length > 0) {
        texto.textContent = diff > 0
            ? `La suma supera el 100% en ${diff}%.`
            : `Faltan ${Math.abs(diff)}% por distribuir.`;
        alerta.classList.remove('hidden');
        alerta.classList.add('flex');
    } else {
        alerta.classList.add('hidden');
        alerta.classList.remove('flex');
    }
}
