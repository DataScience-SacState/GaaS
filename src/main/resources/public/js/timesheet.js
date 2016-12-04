window.onload = function(){
  $("#calendar").fullCalendar({
    events: [
      {
        title: "Minecraft - Matt Merr",
        start  : '2016-12-03T18:00:00',
        end  : '2016-12-03T23:59:00',
      }, 
      {
        title: "CSGO - David Judilla",
        start  : '2016-12-04T12:00:00',
        end  : '2016-12-04T16:59:00',
      }, 
      {
        title: "CS 1.6 - Joey Laguna",
        start  : '2016-12-06T07:00:00',
        end  : '2016-12-06T23:59:00',
      }, 
      {
        title: "Driftin.io - Varun Ved",
        start  : '2016-12-07T13:00:00',
        end  : '2016-12-07T17:59:00',
      }, 
      {
        title: "Pacman - Brandon Sherman",
        start  : '2016-12-04T00:00:00',
        end  : '2016-12-04T05:59:00',
      }, 
    ],
    editable: true,
    overlap: true
  }).fullCalendar('changeView', 'agendaWeek')	

}
