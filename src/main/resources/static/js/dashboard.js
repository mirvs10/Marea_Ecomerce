document.addEventListener('DOMContentLoaded', function() {
    document.querySelectorAll('.sidebar .nav-link').forEach(link => {
        link.addEventListener('click', function(e) {
            e.preventDefault();
            
            const section = this.getAttribute('data-section');
            
            document.querySelectorAll('.content-section').forEach(s => {
                s.style.display = 'none';
            });
            
            document.getElementById('section-' + section).style.display = 'block';
            
            document.querySelectorAll('.sidebar .nav-link').forEach(l => {
                l.classList.remove('active');
            });
            this.classList.add('active');
        });
    });

    document.querySelectorAll('.editar-btn-usuario').forEach(btn => {
        btn.addEventListener('click', function() {
            const id = this.getAttribute('data-id');
            const nombre = this.getAttribute('data-nombre');
            const correo = this.getAttribute('data-correo');
            const telefono = this.getAttribute('data-telefono');
            const rol = this.getAttribute('data-rol');


            document.getElementById('form-title-usuarios').textContent = '✏️ Editar Usuario';
            document.getElementById('usuario-form').setAttribute('action', '/admin/actualizar/' + id);
            document.getElementById('submit-btn-usuarios').innerHTML = '<i class="fas fa-save"></i> Actualizar';

            document.getElementById('password-group').style.display = 'none';
            document.getElementById('confirm-password-group').style.display = 'none';
            document.getElementById('contraseña').removeAttribute('required');
            document.getElementById('confirmarPassword').removeAttribute('required');

            document.getElementById('usuario-id').value = id;
            document.getElementById('nombre').value = nombre;
            document.getElementById('correo').value = correo;
            document.getElementById('telefono').value = telefono || '';
            document.getElementById('tipoUsuario').value = rol === 'ADMIN' ? 'admin' : 'cliente';

            window.scrollTo({ top: 0, behavior: 'smooth' });
        });
    });

    window.resetFormularioUsuarios = function() {
        
        document.getElementById('form-title-usuarios').textContent = '➕ Agregar Nuevo Usuario';
        document.getElementById('usuario-form').setAttribute('action', '/admin/guardar-nuevo');
        document.getElementById('submit-btn-usuarios').innerHTML = '<i class="fas fa-save"></i> Guardar';

        document.getElementById('usuario-form').reset();
        document.getElementById('usuario-id').value = '';

        document.getElementById('password-group').style.display = 'block';
        document.getElementById('confirm-password-group').style.display = 'block';
        document.getElementById('contraseña').setAttribute('required', 'required');
        document.getElementById('confirmarPassword').setAttribute('required', 'required');

    };

    document.querySelectorAll('.btn-danger').forEach(btn => {
        btn.addEventListener('click', function(e) {
            const nombre = this.closest('tr')?.querySelector('td:nth-child(2)')?.textContent || 'este registro';
        });
    });

    const chartCanvas = document.getElementById('actividadChart');
    if (chartCanvas) {
        const ctx = chartCanvas.getContext('2d');
        new Chart(ctx, {
            type: 'line',
            data: {
                labels: ['Ene', 'Feb', 'Mar', 'Abr', 'May', 'Jun'],
                datasets: [{
                    label: 'Usuarios Registrados',
                    data: [5, 10, 15, 20, 25, 30],
                    borderColor: '#198754',
                    backgroundColor: 'rgba(25, 135, 84, 0.1)',
                    tension: 0.4
                }]
            },
            options: {
                responsive: true,
                maintainAspectRatio: true,
                plugins: {
                    legend: {
                        display: true,
                        position: 'top'
                    }
                },
                scales: {
                    y: {
                        beginAtZero: true
                    }
                }
            }
        });
        }

        });

            document.querySelectorAll('.editar-btn-categoria').forEach(btn => {
                btn.addEventListener('click', function() {
                    const id = this.getAttribute('data-id');
                    const nombre = this.getAttribute('data-nombre');
                    const descripcion = this.getAttribute('data-descripcion');

                    document.getElementById('form-title-categorias').innerHTML = 
                        '<i class="fas fa-edit"></i> Editar Categoría';
                    document.getElementById('categoria-form').setAttribute('action', '/admin/categorias/actualizar/' + id);
                    document.getElementById('submit-btn-categorias').innerHTML = 
                        '<i class="fas fa-save"></i> Actualizar Categoría';

                    document.getElementById('categoria-id').value = id;
                    document.getElementById('categoria-nombre').value = nombre;
                    document.getElementById('categoria-descripcion').value = descripcion || '';


                    document.getElementById('categoria-form').scrollIntoView({ behavior: 'smooth', block: 'start' });
                });
            });

            window.resetFormularioCategorias = function() {
                
                document.getElementById('form-title-categorias').innerHTML = 
                    '<i class="fas fa-plus-circle"></i> Agregar Nueva Categoría';
                document.getElementById('categoria-form').setAttribute('action', '/admin/categorias/guardar');
                document.getElementById('submit-btn-categorias').innerHTML = 
                    '<i class="fas fa-save"></i> Guardar Categoría';

                document.getElementById('categoria-form').reset();
                document.getElementById('categoria-id').value = '';

            };

            document.querySelectorAll('.eliminar-btn-categoria').forEach(btn => {
                btn.addEventListener('click', function(e) {
                    const nombre = this.getAttribute('data-nombre');
                });
            });


            document.querySelectorAll('.editar-btn-producto').forEach(btn => {
                btn.addEventListener('click', function() {
                    const id = this.getAttribute('data-id');
                    const nombre = this.getAttribute('data-nombre');
                    const descripcion = this.getAttribute('data-descripcion');
                    const precio = this.getAttribute('data-precio');
                    const imagen = this.getAttribute('data-imagen');
                    const disponible = this.getAttribute('data-disponible');
                    const idCategoria = this.getAttribute('data-categoria');

                    document.getElementById('productos-tab').click();

                    document.getElementById('form-title-productos').innerHTML = 
                        '<i class="fas fa-edit"></i> Editar Producto';
                    document.getElementById('producto-form').setAttribute('action', '/admin/productos/actualizar/' + id);
                    document.getElementById('submit-btn-productos').innerHTML = 
                        '<i class="fas fa-save"></i> Actualizar Producto';

                    document.getElementById('producto-id').value = id;
                    document.getElementById('producto-nombre').value = nombre;
                    document.getElementById('producto-descripcion').value = descripcion || '';
                    document.getElementById('producto-precio').value = precio;
                    document.getElementById('producto-imagen').value = imagen || '';
                    document.getElementById('producto-disponible').value = disponible;
                    document.getElementById('producto-categoria').value = idCategoria;

                    document.getElementById('producto-form').scrollIntoView({ behavior: 'smooth', block: 'start' });
                });
            });

            window.resetFormularioProductos = function() {
                
                document.getElementById('form-title-productos').innerHTML = 
                    '<i class="fas fa-plus-circle"></i> Agregar Nuevo Producto';
                document.getElementById('producto-form').setAttribute('action', '/admin/productos/guardar');
                document.getElementById('submit-btn-productos').innerHTML = 
                    '<i class="fas fa-save"></i> Guardar Producto';

                document.getElementById('producto-form').reset();
                document.getElementById('producto-id').value = '';

            };

            window.filtrarProductosPorCategoria = function() {
                const categoriaSeleccionada = document.getElementById('filtro-categoria').value;
                const filas = document.querySelectorAll('.fila-producto');
                
                
                let contadorVisible = 0;
                
                filas.forEach(fila => {
                    const categoriaProducto = fila.getAttribute('data-categoria');
                    
                    if (categoriaSeleccionada === '' || categoriaProducto === categoriaSeleccionada) {
                        fila.style.display = '';
                        contadorVisible++;
                    } else {
                        fila.style.display = 'none';
                    }
                });
                
            };

            window.limpiarFiltro = function() {
                document.getElementById('filtro-categoria').value = '';
                filtrarProductosPorCategoria();
            };

            window.activarTabCategorias = function() {
                document.getElementById('categorias-tab').click();
                document.getElementById('categoria-nombre').focus();
            };
