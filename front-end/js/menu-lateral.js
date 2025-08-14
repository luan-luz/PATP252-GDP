var itemMenuLateral = document.querySelectorAll('.item-menu-lateral')

function itemSelecionado() {
    itemMenuLateral.forEach((item) =>
        item.classList.remove('clicado')
    )
    this.classList.add('clicado')
}

itemMenuLateral.forEach((item) =>
    item.addEventListener('click', itemSelecionado)    
)

//Bloquear/desbloquear a expansão do menu ao passar o mouse
var btnBlockExp = document.querySelector('#btn-block-exp')
var menuLateral = document.querySelector('.menu-lateral')

btnBlockExp.addEventListener('click', function() {
    menuLateral.classList.toggle('expandir')
})
