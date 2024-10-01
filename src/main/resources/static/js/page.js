
const closeRecipe = document.getElementById('closeRecipe');
const recipe = document.querySelector('.result');


closeRecipe.onclick = () => {
    recipe.style.display = 'none';
}