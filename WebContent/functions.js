

//////////////// RESET VALUES ////////////////
function reset(){
    document.getElementById("currency1").value = '';
    document.getElementById("customer").value = '';
    document.getElementById("currency2").value = '0';
    document.getElementById("result").value = '';
    document.getElementById("ca").innerHTML = "Coverted Amount";
}

/////////////////////////// Database /////////////////////////

////////////////// GET /////////////////////////
function loadHistory(search){
	
    //////////SET LOG DIV HEIGHTEQUAL TO CONVERTER HEIGHT//////////
    var elmnt = document.getElementById("convertWindow");
    var height = elmnt.offsetHeight
    document.getElementById("historycol").style.height = height - 95;
    document.getElementById("row").style.height = height + 100;

    //////////// AJAX REQUEST TO GET DATA ////////////////////
    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function() {
        if(this.readyState == 4 && this.status == 200)
        {
            document.getElementById('history').innerHTML = '';
            var result = this.responseText;

            var results = JSON.parse(result);
            var list = document.createElement("ul");
            var i = 0; //made for getting number of conversions
            results.forEach((history)=>
        {
            i++;
            var listItem = document.createElement("li");
            list.className = "list-group";
            listItem.className = "list-group-item";
            listItem.style.backgroundColor = "inherit";
            listItem.style.border = "0px";
            var data = document.createTextNode(history.name+" converted "+history._from+" euro to "+history._to);
            listItem.appendChild(data);
            list.appendChild(listItem);
            document.getElementById('history').appendChild(list);
        });
            
        }
    }

    xhttp.open("GET", "history?s="+search, true);
    xhttp.send();
}



function loadVisits(){
    
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if(this.readyState == 4 && this.status == 200)
        {
            var result = this.responseText;
            document.getElementById("visit").innerHTML ="<i class='fas fa-eye'></i> "+ result;
        }
    }

    xhttp.open("GET", "visits", true);
    xhttp.send();
}


function sendback()
{
	var c1 = document.getElementById("currency1").value;
    var c2 = document.getElementById("currency2").value;
    var name = document.getElementById("customer").value;
    var result;
    if(c2!=0 && c1!='') {
        if (c2=='INR') {
            c2 = 78.49;
            result = 'Rs.';
        } else if (c2== 'USD') {
            c2 = 1.11;
            result = '$';
        }  else if (c2== 'BPS') {
            c2 = 0.86;
            result = '£';
        } else {
            c2 = 120.41;
            result = '¥';
        }
    }
    
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if(this.readyState == 4 && this.status == 200)
        { 
        	var resultx = this.responseText;
        	document.getElementById("result").value = resultx;
        	loadHistory("");
     
        }
    }

    xhttp.open("GET", "server?c1="+c1+"&c2="+c2+"&name="+name+"&sign="+result, true);
    xhttp.send();
}


function save(){
    
	var doc = new jsPDF();
	var specialElementHandlers = {
	    '#editor': function (element, renderer) {
	        return true;
	    }
	};
	
	doc.fromHTML($('#history').html(), 15, 15, {
        'width': 170,
            'elementHandlers': specialElementHandlers
    });
	
	var d = new Date();
	var rand = d.getTime();
	
    doc.save('history'+rand+'.pdf');
}
