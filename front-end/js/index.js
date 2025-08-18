//Inicia sempre na página home
carregarTela('home');
//Chamar as páginas
async function carregarTela(nomeTela) {
    try {
        const container = document.querySelector('#container');
        console.log('container: ', container);
        console.log('Irá carregar tela: ' + nomeTela)
        const httpResponse = await fetch(`../html/${nomeTela}.html`);
        if (!httpResponse.ok) {throw new Error("Erro ao carregar a página: " + nomeTela)};
        
        const htmlPaginaNova = await httpResponse.text();
        console.log('Mudando id tela: ', nomeTela);
        container.className = nomeTela;
        container.innerHTML = htmlPaginaNova;
        console.log('Carregou tela: ' + nomeTela + '.html');

        console.log('Irá carregar javascript: ' + nomeTela + '.js');
        const jsPaginaNova = await import(`../js/${nomeTela}.js`);
        jsPaginaNova.init;
        console.log('Carregou javascript: ' + nomeTela + '.js');
    } catch (error) {
        console.log(error);
    }
}
//Chamar função carregar tela e deixá-los selecionados ao clicar nos botões do menu lateral
const itens = document.querySelectorAll('.item-menu-lateral');
itens.forEach((item) => {
    item.addEventListener('click', () => {
        destacaItem(item);
        nomeTela = item.dataset.tela;
        console.log('Irá chamar function carregarTela:', nomeTela);
        carregarTela(nomeTela);
        console.log('sucesso function carregarTela:', nomeTela);
    })
})
//Essa função deixa o item clicado destacado. Por padrão a home vem destacada
function destacaItem(itemClicado) {
    itens.forEach((item) =>
        item.classList.remove('clicado')
    )
    itemClicado.classList.add('clicado')
}

//Bloquear/desbloquear a expansão do menu ao passar o mouse
var btnBloqExp = document.querySelector('#btn-bloq-exp')
var menuLateral = document.querySelector('.menu-lateral')

btnBloqExp.addEventListener('click', function() {
    menuLateral.classList.toggle('bloq-exp')
})
