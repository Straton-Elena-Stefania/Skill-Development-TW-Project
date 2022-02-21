let btn = document.getElementsByClassName("modalWindowOpener");
let noScrollToggle = document.getElementsByTagName("body");
noScrollToggle[0].style.overflow = "hidden";

for (let i = 0; i < btn.length; i++) {
  let thisBtn = btn[i];
  thisBtn.addEventListener("click", function () {
    let modal = document.getElementById(this.dataset.modal);
    modal.style.display = "block";
  }, false);
}