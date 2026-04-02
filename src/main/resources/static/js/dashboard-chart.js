document.addEventListener('DOMContentLoaded', function () {
    const ctx = document.getElementById('actividadMensualChart').getContext('2d');

    new Chart(ctx, {
        type: 'line',
        data: {
            labels: ['Enero', 'Febrero', 'Marzo', 'Abril', 'Mayo', 'Junio'],
            datasets: [
                {
                    label: 'Registros',
                    data: [10, 25, 30, 45, 60, 75],
                    borderColor: '#28a745',
                    backgroundColor: 'rgba(40, 167, 69, 0.2)',
                    tension: 0.3,
                    fill: true
                },
                {
                    label: 'Logins',
                    data: [5, 18, 22, 35, 50, 70],
                    borderColor: '#007bff',
                    backgroundColor: 'rgba(0, 123, 255, 0.2)',
                    tension: 0.3,
                    fill: true
                }
            ]
        },
        options: {
            responsive: true,
            plugins: {
                legend: { position: 'top' }
            },
            scales: {
                y: {
                    beginAtZero: true,
                    title: {
                        display: true,
                        text: 'Cantidad de usuarios'
                    }
                }
            }
        }
    });
});