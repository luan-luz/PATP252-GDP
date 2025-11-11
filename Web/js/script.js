  
//abre a side bar
document.getElementById('open_btn').addEventListener('click', function () {
  document.getElementById('sidebar').classList.toggle('open-sidebar');
})


//ajuste do icone de abrir
    const toggleBtn = document.getElementById('open_btn');
    const sidebar = document.querySelector('.sidebar');
 
    toggleBtn.addEventListener('click', function () { 
        if (sidebar.style.left === '0px') {
            sidebar.style.left = ' 1px'; // Fecha a sidebar
            toggleBtn.style.left = '3px'; 
        } else {
            sidebar.style.left = '0px'; // Abre a sidebar
            toggleBtn.style.left = '115px';  
        }
    });

 

window.onload = function() { 
    var ctxPie = document.getElementById('pieChart').getContext('2d');
    var pieChart = new Chart(ctxPie, {
        type: 'pie', // Tipo do gráfico (pizza)
        data: {
            labels: ['Patrimônio', 'Itens', 'Ativos'], // Rótulos
            datasets: [{
                label: 'Gráfico de Pizza',
                data: [300, 50, 100], // Valores para cada segmento
                backgroundColor: ['#FF5733', '#33FF57', '#3357FF'], // Cores
                borderColor: ['#FFFFFF', '#FFFFFF', '#FFFFFF'], // Cor da borda
                borderWidth: 2 // Espessura da borda
            }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: {
                    position: 'top'
                }
            }
        }
    });

    // Gráfico de Linha
    var ctxLine = document.getElementById('lineChart').getContext('2d');
    var lineChart = new Chart(ctxLine, {
        type: 'line', // Tipo do gráfico (linha)
        data: {
            labels: ['Jan', 'Fev', 'Mar', 'Abr', 'Mai', 'Jun'], // Rótulos (meses)
            datasets: [{
                label: 'Gráfico de Linha',
                data: [65, 59, 80, 81, 56, 55], // Valores para cada mês
                borderColor: '#4e73df', // Cor da linha
                fill: false, // Não preenche a área abaixo da linha
                tension: 0.1 // Define a suavização da linha
            }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: {
                    position: 'top'
                }
            }
        }
    });
};
