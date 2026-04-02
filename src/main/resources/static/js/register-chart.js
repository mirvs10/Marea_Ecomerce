document.addEventListener('DOMContentLoaded', function () {
    const ctx = document.getElementById('registroChart').getContext('2d');

    new Chart(ctx, {
        type: 'bar',
        data: {
            labels: ['Enero', 'Febrero', 'Marzo'],
            datasets: [{
                label: 'Usuarios Registrados',
                data: [10, 25, 30],
                backgroundColor: [
                    'rgba(40, 167, 69, 0.7)',
                    'rgba(40, 167, 69, 0.8)',
                    'rgba(40, 167, 69, 1)'
                ],
                borderRadius: 6,
                borderWidth: 1
            }]
        },
        options: {
            responsive: true,
            plugins: {
                title: {
                    display: true,
                    text: 'Usuarios Registrados por Mes',
                    font: { size: 20 }
                },
                legend: { display: false }
            },
            scales: {
                y: {
                    beginAtZero: true,
                    ticks: {
                        stepSize: 10
                    }
                }
            }
        }
    });
});
