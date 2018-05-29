class Session {

  var atMenu;
  var atChoose;
  var atInventory;
  var atSureSwitch;
  var atSwitch;
  var atSureEat;
  var atTravel;
  var atBattle;
  var atBattleChoose;
  var atEat;
  var atUse;
  var atLevel;
  var atSureDiscard;
  var atDiscard;
  var atWield;
  var atSureInitial;
  var atInitial;

  Session() {
    atMenu = true;
    atChoose = false;
    atInventory = false;
    atSureSwitch = false;
    atSwitch = false;
    atSureEat = false;
    atTravel = false;
    atBattle = false;
    atBattleChoose = false;
    atEat = false;
    atUse = false;
    atLevel = false;
    atSureDiscard = false;
    atDiscard = false;
    atWield = false;
    atSureInitial = false;
    atInitial = false;
  }

  function load(id, db) {
    dbo.collection("sessions").find({_id: id},{_id: 0, current}).toArray(function(err, result) {
      if (err) throw err;
      atMenu = result.atMenu;
      atChoose = result.atChoose;
      atInventory = result.atInventory;
      atSureSwitch = result.atSureSwitch;
      atSwitch = result.atSwitch;
      atSureEat = result.atSureEat;
      atTravel = result.atTravel;
      atBattle = result.atBattle;
      atBattleChoose = result.atBattleChoose;
      atEat = result.atEat;
      atUse = result.atUse;
      atLevel = result.atLevel;
      atSureDiscard = result.atSureDiscard;
      atDiscard = result.atDiscard;
      atWield = result.atWield;
      atSureInitial = result.atSureInitial;
      atInitial = result.atInitial;
    });
  }

  function store(id, db) {

  }

  getAtMenu() {
    return atMenu;
  }
  getAtChoose() {
    return atChoose;
  }
  getAtInventory() {
    return atInventory;
  }
  getAtSureSwitch() {
    return atSureSwitch;
  }
  getAtSwitch() {
    return atSwitch;
  }
  getAtSureEat() {
    return atSureEat;
  }
  getAtTravel() {
    return atTravel;
  }
  getAtBattle() {
    return atBattle;
  }
  getAtBattleChoose() {
    return atBattleChoose;
  }
  getAtEat() {
    return atEat;
  }
  getAtUse() {
    return atUse;
  }
  getAtLevel() {
    return atLevel;
  }
  getAtSureDiscard() {
    return atSureDiscard;
  }
  getAtDiscard() {
    return atDiscard;
  }
  getAtWield() {
    return atWield;
  }
  getAtSureInitial() {
    return atSureInitial;
  }
  getAtInitial() {
    return atInitial;
  }

  setAtMenu(b) {
    atMenu = b;
  }
  setAtChoose(b) {
    atChoose = b;
  }
  setAtInventory(b) {
    atInventory = b;
  }
  setAtSureSwitch(b) {
    atSureSwitch = b;
  }
  setAtSwitch(b) {
    atSwitch = b;
  }
  setAtSureEat(b) {
    atSureEat = b;
  }
  setAtTravel(b) {
    atTravel = b;
  }
  setAtBattle(b) {
    atBattle = b;
  }
  setAtBattleChoose(b) {
    atBattleChoose = b;
  }
  setAtEat(b) {
    atEat = b;
  }
  setAtUse(b) {
    atUse = b;
  }
  setAtLevel(b) {
    atLevel = b;
  }
  setAtSureDiscard(b) {
    atSureDiscard = b;
  }
  setAtDiscard(b) {
    atDiscard = b;
  }
  setAtWield(b) {
    atWield = b;
  }
  setAtSureInitial(b) {
    atSureInitial = b;
  }
  setAtInitial(b) {
    atInitial = b;
  }

}
