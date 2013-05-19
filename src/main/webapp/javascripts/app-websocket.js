var ws = null;

function zmienStanUI(connected) {
  if (connected) {
    $("#status-polaczony").removeClass('visually-hidden');
    $("#status-rozlaczony").addClass('visually-hidden');
    $("#button-wyslij").addClass('disabled');
  } else {
    $("#status-polaczony").addClass('visually-hidden');
    $("#status-rozlaczony").removeClass('visually-hidden');
    $("#button-wyslij").removeClass('disabled');
  }
}

function ustanowPolaczenie() {
  var protocol;
  if (window.location.protocol == 'http:') {
    protocol = 'ws://';
  } else {
    protocol = 'wss://';
  }
  var target = protocol + window.location.host + '/websocket/service';

  if ('WebSocket' in window) {
    ws = new WebSocket(target);
  } else if ('MozWebSocket' in window) {
    ws = new MozWebSocket(target);
  } else {
    $('#przegladarkaBezWebsocket').reveal();
    return;
  }
  ws.onopen = function () {
    zmienStanUI(true);
    wyslijDaneDoSerwera();
  };
  ws.onmessage = function (event) {
    var dane = JSON.parse(event.data);
    log(dane.nazwa + ' : ' + dane.opis);
  };
  ws.onclose = function () {
    zmienStanUI(false);
  };
}

function przerwijPolaczenie() {
  if (ws != null) {
    ws.close();
    ws = null;
  }
  zmienStanUI(false);
}

function wyslijDane() {
  wyczyscKonsole();
  ustanowPolaczenie();
}

function wyslijDaneDoSerwera() {
  var obiektDanych = {};
  obiektDanych.nazwa = $("#input-pierwszy").val();
  obiektDanych.opis = $('#input-drugi').val();
  ws.send(JSON.stringify(obiektDanych));
}

function wyczyscKonsole() {
  $('#konsola').empty();
}

function log(message) {
  var konsola = document.getElementById('konsola');
  var p = document.createElement('p');
  p.style.wordWrap = 'break-word';
  p.appendChild(document.createTextNode(message));
  konsola.appendChild(p);
}