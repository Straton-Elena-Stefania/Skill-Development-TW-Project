let favoriteButton = document.getElementsByClassName('heartSkillButton');
let floatingTexts = document.getElementsByClassName('floatingFavoritedText');

for (let i = 0; i < favoriteButton.length; i++) {
    favoriteButton[i].addEventListener("click", function () {
        floatingTexts[i].style.display = "block";
        window.setTimeout(function () { stopFavoritedText(i); }, 2500);

        console.log(i);
    }, false);
}


function stopFavoritedText(i) {
    floatingTexts[i].style.display = " none";
}