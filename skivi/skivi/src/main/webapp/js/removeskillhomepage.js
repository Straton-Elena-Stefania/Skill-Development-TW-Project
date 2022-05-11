function setSkillToRemove(skillId) {
    var hiddenInputSkillRemove = document.getElementById("whatToRemove");
    hiddenInputSkillRemove.value = skillId;
    console.log(skillId);
    console.log("set to: " + hiddenInputSkillRemove.value);
}

function unsetSkillToRemove() {
    var hiddenInputSkillRemove = document.getElementById("whatToRemove");
    hiddenInputSkillRemove.value = null;
    console.log("made null: " + hiddenInputSkillRemove.value);
}