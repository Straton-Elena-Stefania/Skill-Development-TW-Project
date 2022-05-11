const settingsBar = document.getElementById('settingsBar');
    settingsBar.addEventListener('click', (event) => {
      if (event.target.tagName === 'I') {
        const lastActiveButton = document.getElementsByClassName('active')[0];
        const currentActiveButton = event.target.parentNode;

        lastActiveButton.classList.remove('active');
        currentActiveButton.classList.add('active');
        const settingsContainerElements = Array.from(document.getElementsByClassName('tab'));
        if(event.target.children[0].innerText === "Profile"){

          settingsContainerElements.map(tab => {
            if(tab.id === "profileTab"){
              tab.classList.remove('hideTab');
            } else {
              tab.classList.add('hideTab')
            }
          })
        }
        else if(event.target.children[0].innerText === "Settings"){
          settingsContainerElements.map(tab => {
            if(tab.id === "settingsTab"){
              tab.classList.remove('hideTab');
            } else {
              tab.classList.add('hideTab')
            }
          })
        }
      }
    });