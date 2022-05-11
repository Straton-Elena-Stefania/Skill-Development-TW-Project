const skillAddButton = document.getElementById('addButton');
const achievementReference = document.getElementById('achievementReference')
skillAddButton.addEventListener('click',(event) =>{
  event.preventDefault();
  event.stopPropagation();
  const list = document.getElementById('skillList');
  const skillInput = document.getElementById('skillInput');
  const skill = skillInput.value;
  if(skill){
    const newSKill = document.createElement('li');
  newSKill.appendChild(document.createTextNode(skill));
  list.appendChild(newSKill);
  skillInput.value = '';
  }
})
const settingsBar = document.getElementById('settingsBar');
  settingsBar.addEventListener('click', (event) => {
    event.preventDefault();
    event.stopPropagation();
    if (event.target.tagName === 'A') {
      const lastActiveButton = document.getElementsByClassName('active')[0];
      const currentActiveButton = event.target.parentNode;

      lastActiveButton.classList.remove('active');
      currentActiveButton.classList.add('active');

      const settingsContainerElements = Array.from(document.getElementsByClassName('tab'));
      if(event.target.innerText === "Info"){

        settingsContainerElements.map(tab => {
          if(tab.id === "infoTab"){
            tab.classList.remove('hideTab');
          } else {
            tab.classList.add('hideTab')
          }
        })
      }
      else if(event.target.innerText === "🏆 Achievements"){
        settingsContainerElements.map(tab => {
          if(tab.id === "scoresTab"){
            tab.classList.remove('hideTab');
          } else {
            tab.classList.add('hideTab')
          }
        })
      }
    }
  });