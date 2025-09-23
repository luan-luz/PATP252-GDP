// Dados simulados (normalmente viriam do banco de dados)
let dadosMovimentacoes = [
    { nfe: '001', data: '2023-01-01', fornecedor: 'Fornecedor A', movimentacao: 'Em uso', descricao: 'Ativo em uso no escritório' },
    { nfe: '002', data: '2023-03-15', fornecedor: 'Fornecedor B', movimentacao: 'Manutenção', descricao: 'Impressora em manutenção' },
    { nfe: '003', data: '2023-05-10', fornecedor: 'Fornecedor C', movimentacao: 'Transferência', descricao: 'Transferido para o setor B' },
    { nfe: '004', data: '2023-07-20', fornecedor: 'Fornecedor D', movimentacao: 'Baixa (Descarte)', descricao: 'Equipamento fora de uso' },
    { nfe: '005', data: '2023-09-05', fornecedor: 'Fornecedor E', movimentacao: 'Baixa (Furto)', descricao: 'Computador furtado' },
];

// Função para filtrar o relatório
document.getElementById('filtrarRelatorio').addEventListener('click', function() {
    const dataInicio = document.getElementById('filtroDataInicio').value;
    const dataFim = document.getElementById('filtroDataFim').value;
    const tipoMovimentacao = document.getElementById('filtroMovimentacao').value;

    // Filtrando dados
    const dadosFiltrados = dadosMovimentacoes.filter(item => {
        const dataItem = new Date(item.data);
        const dataInicioValida = dataInicio ? new Date(dataInicio) <= dataItem : true;
        const dataFimValida = dataFim ? new Date(dataFim) >= dataItem : true;
        const tipoMovimentacaoValido = tipoMovimentacao ? item.movimentacao === tipoMovimentacao : true;

        return dataInicioValida && dataFimValida && tipoMovimentacaoValido;
    });

    // Atualizando a tabela com os dados filtrados
    atualizarTabela(dadosFiltrados);
});

// Função para atualizar a tabela
function atualizarTabela(dados) {
    const tabela = document.getElementById('movimentacaoTabela').getElementsByTagName('tbody')[0];
    tabela.innerHTML = ''; // Limpa a tabela

    dados.forEach(item => {
        const novaLinha = tabela.insertRow();
        novaLinha.insertCell(0).textContent = item.nfe;
        novaLinha.insertCell(1).textContent = item.data;
        novaLinha.insertCell(2).textContent = item.fornecedor;
        novaLinha.insertCell(3).textContent = item.movimentacao;
        novaLinha.insertCell(4).textContent = item.descricao;
    });
}

// Função para exportar o relatório para CSV
document.getElementById('exportarRelatorio').addEventListener('click', function() {
    const csv = gerarCSV(dadosMovimentacoes);
    baixarCSV(csv);
});

// Função para gerar CSV
function gerarCSV(dados) {
    const cabecalho = ['Nº NFe', 'Data da Aquisição', 'Fornecedor', 'Tipo de Movimentação', 'Descrição da Movimentação'];
    const linhas = dados.map(item => [item.nfe, item.data, item.fornecedor, item.movimentacao, item.descricao]);

    const csv = [cabecalho, ...linhas]
        .map(linha => linha.join(','))
        .join('\n');

    return csv;
}

// Função para baixar o CSV
function baixarCSV(csv) {
    const link = document.createElement('a');
    link.href = 'data:text/csv;charset=utf-8,' + encodeURIComponent(csv);
    link.target = '_blank';
    link.download = 'relatorio_movimentacoes.csv';
    link.click();
}

// Inicializa a tabela com todos os dados
atualizarTabela(dadosMovimentacoes);









































 
// Dados simulados (normalmente viriam do banco de dados)
let dadosMovimentacoes = [
    { produto: 'Computador', nfe: '001', data: '2023-01-01', fornecedor: 'Fornecedor A', setor: 'Setor A', movimentacao: 'Em uso', descricao: 'Ativo em uso no escritório' },
    { produto: 'Impressora', nfe: '002', data: '2023-03-15', fornecedor: 'Fornecedor B', setor: 'Setor B', movimentacao: 'Manutenção', descricao: 'Impressora em manutenção' },
    { produto: 'Mesa', nfe: '003', data: '2023-05-10', fornecedor: 'Fornecedor C', setor: 'Setor C', movimentacao: 'Transferência', descricao: 'Transferido para o setor B' },
    { produto: 'Cadeira', nfe: '004', data: '2023-07-20', fornecedor: 'Fornecedor D', setor: 'Setor D', movimentacao: 'Baixa (Descarte)', descricao: 'Equipamento fora de uso' },
    { produto: 'Monitor', nfe: '005', data: '2023-09-05', fornecedor: 'Fornecedor E', setor: 'Setor E', movimentacao: 'Baixa (Furto)', descricao: 'Computador furtado' },
];

// Função para adicionar uma nova linha à tabela
document.getElementById('movimentacaoForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Impede o envio do formulário

    // Pegando os dados do formulário
    const produto = document.getElementById('produto').value;
    const nfe = document.getElementById('nfe').value;
    const data = document.getElementById('data').value;
    const fornecedor = document.getElementById('fornecedor').value;
    const setor = document.getElementById('setor').value;
    const movimentacao = document.getElementById('movimentacao').value;
    const descricao = document.getElementById('descricao').value;

    // Verificando se todos os campos foram preenchidos
    if (produto && nfe && data && fornecedor && setor && movimentacao && descricao) {
        // Criando uma nova linha na tabela
        const tabela = document.getElementById('movimentacaoTabela').getElementsByTagName('tbody')[0];
        const novaLinha = tabela.insertRow();

        // Criando células para cada campo
        const celulaProduto = novaLinha.insertCell(0);
        const celulaNfe = novaLinha.insertCell(1);
        const celulaData = novaLinha.insertCell(2);
        const celulaFornecedor = novaLinha.insertCell(3);
        const celulaSetor = novaLinha.insertCell(4);
        const celulaMovimentacao = novaLinha.insertCell(5);
        const celulaDescricao = novaLinha.insertCell(6);

        // Atribuindo os valores às células
        celulaProduto.textContent = produto;
        celulaNfe.textContent = nfe;
        celulaData.textContent = data;
        celulaFornecedor.textContent = fornecedor;
        celulaSetor.textContent = setor;
        celulaMovimentacao.textContent = movimentacao;
        celulaDescricao.textContent = descricao;

        // Limpando o formulário após adicionar
        document.getElementById('movimentacaoForm').reset();
    } else {
        alert("Por favor, preencha todos os campos.");
    }
});

// Função para filtrar as movimentações por setor
document.getElementById('filtrarRelatorio').addEventListener('click', function() {
    const setorFiltro = document.getElementById('filtroSetor').value;

    // Filtrando dados
    const dadosFiltrados = dadosMovimentacoes.filter(item => {
        return !setorFiltro || item.setor === setorFiltro; // Filtra ou retorna todos os dados
    });

    // Atualizando a tabela com os dados filtrados
    atualizarTabela(dadosFiltrados);
});

// Função para atualizar a tabela
function atualizarTabela(dados) {
    const tabela = document.getElementById('movimentacaoTabela').getElementsByTagName('tbody')[0];
    tabela.innerHTML = ''; // Limpa a tabela

    dados.forEach(item => {
        const novaLinha = tabela.insertRow();
        novaLinha.insertCell(0).textContent = item.produto;
        novaLinha.insertCell(1).textContent = item.nfe;
        novaLinha.insertCell(2).textContent = item.data;
        novaLinha.insertCell(3).textContent = item.fornecedor;
        novaLinha.insertCell(4).textContent = item.setor;
        novaLinha.insertCell(5).textContent = item.movimentacao;
        novaLinha.insertCell(6).textContent = item.descricao;
    });
}

// Inicializa a tabela
atualizarTabela(dadosMovimentacoes);
