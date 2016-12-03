window.onload = function(){
	var variables = new Set();

	function GetDates(startDate, daysToAdd) {
    	var aryDates = [];

    	for (var i = 0; i <= daysToAdd; i++) {
        	var currentDate = new Date();
        	currentDate.setDate(startDate.getDate() + i);
        	aryDates.push(DayAsString(currentDate.getDay()) + ", " + currentDate.getDate() + " " + MonthAsString(currentDate.getMonth()) + " " + currentDate.getFullYear());
    	}
    	return aryDates;
    }

    function MonthAsString(monthIndex) {
	    var d = new Date();
	    var month = new Array();
	    month[0] = "January";
	    month[1] = "February";
	    month[2] = "March";
	    month[3] = "April";
	    month[4] = "May";
	    month[5] = "June";
	    month[6] = "July";
	    month[7] = "August";
	    month[8] = "September";
	    month[9] = "October";
	    month[10] = "November";
	    month[11] = "December";

	    return month[monthIndex];
	}

	function DayAsString(dayIndex) {
	    var weekdays = new Array(7);
	    weekdays[0] = "Sunday";
	    weekdays[1] = "Monday";
	    weekdays[2] = "Tuesday";
	    weekdays[3] = "Wednesday";
	    weekdays[4] = "Thursday";
	    weekdays[5] = "Friday";
	    weekdays[6] = "Saturday";

	    return weekdays[dayIndex];
	}


	var select = document.getElementById("day")
	var startDate = new Date();
	var aryDates = GetDates(startDate, 7);

	for(var i = 0; i < aryDates.length; i++){
		var opt = aryDates[i];
		var el = document.createElement("option");
		el.textContent = opt;
		select.appendChild(el);
	}
	
}




