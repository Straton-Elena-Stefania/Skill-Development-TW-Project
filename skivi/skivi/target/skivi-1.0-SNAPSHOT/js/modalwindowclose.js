let closeBtn = document.getElementsByClassName("modalWindowCloser");
let scrollToggle = document.getElementsByTagName("body");
scrollToggle[0].style.overflow = "visible";

for (let i = 0; i < closeBtn.length; i++) {
  let thisBtn = closeBtn[i];
  thisBtn.addEventListener("click", function () {
    let modal = document.getElementById(this.dataset.modal);
    modal.style.display = "none";
  }, false);
}