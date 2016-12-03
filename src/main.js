document.onload = function(){
	console.log('hello')
	var variables = []

	const varInput = document.getElementById("var-input")

	updateVarList(variables) {
		const varList = document.getElementById("var-list")
		let ol = document.createElement('ol')
		ol.innerHTML = variables.map(variable => `<li>${variable}</li>`)
		varList.innerHTML = ol
	}	

	varInput.addEventListener('keydown', function(e) {
		if (e.key == 'Enter') {
			variables.push(e.target.value)
			e.target.value = ''
			updateVarList()
		}
	})

}



/*
              T E C H D E B T
            B             B E
          E             E   C
        D             D     H
      H             H       D
    C             C         E
  E             E           B
T E C H D E B T             T
E             E           B
C             C         E
H             H       D
D             D     H
E             E   C
B             B E
T E C H D E B T



*/