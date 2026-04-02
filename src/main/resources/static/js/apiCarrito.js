async function agregarCarrito(idProducto) {
    try {
        await fetch(`/api/carrito/agregar/${idProducto}?cantidad=1`, {
            method: 'POST'
        });

        await cargarCarrito();       
        alert("Producto agregado al carrito");

    } catch (err) {
        console.error(err);
        alert("Error al agregar");
    }
}

async function cargarCarrito() {
    const res = await fetch("/api/carrito");
    const carrito = await res.json();

    document.getElementById("badgeCarrito").innerText = carrito.totalCantidad || 0;

    let html = "";
    carrito.items.forEach(i => {
        html += `
            <div class="d-flex justify-content-between border-bottom py-2">
                <div>
                    <strong>${i.producto.nombre}</strong>
                    <br>
                    <small>S/ ${i.producto.precio}</small>
                </div>
                <div class="text-end">
                    <span class="badge bg-primary">${i.cantidad}</span>
                </div>
            </div>
        `;
    });
    document.getElementById("itemsCarrito").innerHTML = html;

    document.getElementById("totalCarrito").innerText = "S/ " + carrito.total;
}

async function vaciarCarrito() {
    await fetch("/api/carrito/vaciar", { method: "DELETE" });
    await cargarCarrito();
}
document.addEventListener("DOMContentLoaded", cargarCarrito);
