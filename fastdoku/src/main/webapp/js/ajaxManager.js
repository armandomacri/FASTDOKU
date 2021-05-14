function AjaxManager(){}

AjaxManager.performAjaxRequest =
    function(method, url, async, data, responseFunction){

        var xmlHttp = null; //Inizializzo l'oggetto xmlHttp

        // qui valutiamo la tipologia di browser utilizzato per selezionare la tipologia di oggetto da creare.
        // Se sono in un browser Mozilla/Safari, utilizzo l'oggetto XMLHttpRequest per lo scambio di dati tra browser e server.
        if (window.XMLHttpRequest) {
            xmlHttp = new XMLHttpRequest();
        }

        // Se sono in un Browser di Microsoft (IE), utilizzo Microsoft.XMLHTTP
        //che rappresenta la classe di riferimento per questo browser
        else if (window.ActiveXObject) {
            xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
        }

        if (xmlHttp === null) {
            window.alert("Il tuo browser non supporta AJAX!");
            return;
        }

        //Apro il canale di connessione per regolare il tipo di richiesta.
        //Passo come parametri il tipo di richiesta, url e se è o meno un operazione asincrona.
        xmlHttp.open(method, url, async);
        //setto l'header dell'oggetto
        xmlHttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
        xmlHttp.onreadystatechange = function (){

            /*
            Gli stai di una richiesta possono essere 5
                * 0 - UNINITIALIZED
                * 1 - LOADING
                * 2 - LOADED
                * 3 - INTERACTIVE
                * 4 - COMPLETE
            */
            if (xmlHttp.readyState == 4){ //Se lo stato è completo
                var response = xmlHttp.responseText;
                //Aggiorno la pagina con la risposta restituita dalla precendete richiesta dal web server.
                //Quando la richiesta è terminata il responso della richiesta è disponibie come responseText.
                responseFunction(response);
            }
        }

        /* Passo alla richiesta i valori del form in modo da generare l'output desiderato */
        //dati == null serve GET
        //dati == "stringa" serve post
        xmlHttp.send(data);
    }

AjaxManager.updatePoints =
    function(points){
        var url = "./update-servlet";

        AjaxManager.performAjaxRequest("POST", url, true, "points=" + points, showModal);
    }