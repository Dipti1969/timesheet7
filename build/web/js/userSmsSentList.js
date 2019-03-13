  function validateSearch() {
    retVal=true;
    document.getElementById("errorStartDate").style.display="none";
    document.getElementById("errorStartDateFormat").style.display="none";
    document.getElementById("errorInvalidStartDate").style.display="none";
    document.getElementById("errorEndDate").style.display="none";
    document.getElementById("errorEndDateFormat").style.display="none";
    document.getElementById("errorInvalidEndDate").style.display="none";
    document.getElementById("errorpickers").style.display="none";
    var rangeId = document.getElementById("rangeId");
    var startDate = document.getElementById("startDateId");
    var endDate = document.getElementById("endDateId");
    var dateFormat = document.getElementById("dateFormatId").value;
    if(rangeId.value == 'custom') {

      if(startDate.value.trim() == "" || startDate.value.trim() == dateFormat) {
        document.getElementById("errorStartDate").style.display="";
        document.getElementById("errorpickers").style.display="";
        document.getElementById("searchpannel").style.height="50px";
        if (retVal) {
          startDate.focus();
        }
        retVal=false;
      } else if(startDate.value.trim() != "") {
        if (!checkDateFormat(startDate.value,dateFormat)){
          document.getElementById("errorStartDateFormat").style.display="";
          document.getElementById("errorpickers").style.display="";
          document.getElementById("searchpannel").style.height="50px";
          if (retVal) {
            startDate.focus();
          }
          retVal=false;
        } else if (!checkValidDate(startDate.value,dateFormat)) {
          document.getElementById("errorInvalidStartDate").style.display="";
          document.getElementById("errorpickers").style.display="";
          document.getElementById("searchpannel").style.height="50px";
          if (retVal) {
            startDate.focus();
          }
          retVal=false;
        }
      }

      if(endDate.value.trim() == "" || endDate.value.trim() == dateFormat) {
        document.getElementById("errorEndDate").style.display="";
        document.getElementById("errorpickers").style.display="";
        document.getElementById("searchpannel").style.height="50px";
        if (retVal) {
          endDate.focus();
        }
        retVal=false;
      } else if(endDate.value.trim() != "") {
        if (!checkDateFormat(endDate.value,dateFormat)){
          document.getElementById("errorEndDateFormat").style.display="";
          document.getElementById("errorpickers").style.display="";
          document.getElementById("searchpannel").style.height="50px";
          if (retVal) {
            endDate.focus();
          }
          retVal=false;
        } else if (!checkValidDate(endDate.value,dateFormat)) {
          document.getElementById("errorInvalidEndDate").style.display="";
          document.getElementById("errorpickers").style.display="";
          document.getElementById("searchpannel").style.height="50px";
          if (retVal) {
            endDate.focus();
          }
          retVal=false;
        }
      }
    }
    //alert(retVal);
      return retVal;
    }

    function changeTextColor() {
      var query=  document.getElementById("queryId");
      if (query.value =="search patient") {
        query.value ="";
        query.style.color="#000";
      } else {
        query.style.color="#000";
      }
    }
    function changeTxtColor() {
      var query=  document.getElementById("queryId");
      if (query.value =="search user name") {
        query.value ="";
        query.style.color="#000";
      } else {
        query.style.color="#000";
      }
    }

    function changeStartTextColor() {
      var startDate = document.getElementById("startDateId");
      var dateFormat = document.getElementById("dateFormatId").value;
      if (startDate.value == dateFormat) {
          startDate.value ="";
         startDate.style.fontSize="12px";
        startDate.style.color="#000";
      } else {
        startDate.style.fontSize="12px";
        startDate.style.color="#000";
      }
    }

    function changeEndTextColor() {
      var endDate = document.getElementById("endDateId");
      var dateFormat = document.getElementById("dateFormatId").value;
      if (endDate.value == dateFormat) {
        endDate.value ="";
        endDate.style.fontSize="12px";
        endDate.style.color="#000";
         
      } else {
        endDate.style.fontSize="12px";
        endDate.style.color="#000";
      }
    }

    function displayDatepicker(combo){
      var dpks = document.getElementById("datepicker");
      document.getElementById("errorStartDate").style.display="none";
    document.getElementById("errorStartDateFormat").style.display="none";
    document.getElementById("errorInvalidStartDate").style.display="none";
    document.getElementById("errorEndDate").style.display="none";
    document.getElementById("errorEndDateFormat").style.display="none";
    document.getElementById("errorInvalidEndDate").style.display="none";
    document.getElementById("searchpannel").style.height="33px";
      if(combo.value == 'custom') {
        dpks.style.display = '';
        var dateFormat = document.getElementById("dateFormatId").value;
        document.getElementById("startDateId").value =getCurrentDate(dateFormat);
        document.getElementById("endDateId").value =getCurrentDate(dateFormat);
        document.getElementById("searchpannel").style.width="680px";
      } else {
        document.getElementById("errorpickers").style.display="none";
        dpks.style.display = 'none';
        document.getElementById("searchpannel").style.width="380px";

      }
    }

  function validateNewSearch() {
    retVal=true;
    document.getElementById("errorStartDate").style.display="none";
    document.getElementById("errorStartDateFormat").style.display="none";
    document.getElementById("errorInvalidStartDate").style.display="none";
    document.getElementById("errorEndDate").style.display="none";
    document.getElementById("errorEndDateFormat").style.display="none";
    document.getElementById("errorInvalidEndDate").style.display="none";
    document.getElementById("errorpickers").style.display="none";
    var rangeId = document.getElementById("rangeId");
    var startDate = document.getElementById("startDateId");
    var endDate = document.getElementById("endDateId");
    var dateFormat = document.getElementById("dateFormatId").value;
    if(rangeId.value == 'custom') {

      if(startDate.value.trim() == "" || startDate.value.trim() == dateFormat) {
        document.getElementById("errorStartDate").style.display="";
        document.getElementById("errorpickers").style.display="";
        document.getElementById("searchpannell").style.height="50px";
        if (retVal) {
          startDate.focus();
        }
        retVal=false;
      } else if(startDate.value.trim() != "") {
        if (!checkDateFormat(startDate.value,dateFormat)){
          document.getElementById("errorStartDateFormat").style.display="";
          document.getElementById("errorpickers").style.display="";
          document.getElementById("searchpannell").style.height="50px";
          if (retVal) {
            startDate.focus();
          }
          retVal=false;
        } else if (!checkValidDate(startDate.value,dateFormat)) {
          document.getElementById("errorInvalidStartDate").style.display="";
          document.getElementById("errorpickers").style.display="";
          document.getElementById("searchpannell").style.height="50px";
          if (retVal) {
            startDate.focus();
          }
          retVal=false;
        }
      }

      if(endDate.value.trim() == "" || endDate.value.trim() == dateFormat) {
        document.getElementById("errorEndDate").style.display="";
        document.getElementById("errorpickers").style.display="";
        document.getElementById("searchpannell").style.height="50px";
        if (retVal) {
          endDate.focus();
        }
        retVal=false;
      } else if(endDate.value.trim() != "") {
        if (!checkDateFormat(endDate.value,dateFormat)){
          document.getElementById("errorEndDateFormat").style.display="";
          document.getElementById("errorpickers").style.display="";
          document.getElementById("searchpannell").style.height="50px";
          if (retVal) {
            endDate.focus();
          }
          retVal=false;
        } else if (!checkValidDate(endDate.value,dateFormat)) {
          document.getElementById("errorInvalidEndDate").style.display="";
          document.getElementById("errorpickers").style.display="";
          document.getElementById("searchpannell").style.height="50px";
          if (retVal) {
            endDate.focus();
          }
          retVal=false;
        }
      }
    }
    //alert(retVal);
      return retVal;
    }

    function displayDtpicker(combo){
    var dpks = document.getElementById("datepickers");
    document.getElementById("errorStartDate").style.display="none";
    document.getElementById("errorStartDateFormat").style.display="none";
    document.getElementById("errorInvalidStartDate").style.display="none";
    document.getElementById("errorEndDate").style.display="none";
    document.getElementById("errorEndDateFormat").style.display="none";
    document.getElementById("errorInvalidEndDate").style.display="none";
    document.getElementById("searchpannell").style.height="33px";
      if(combo.value == 'custom') {
        dpks.style.display = '';
        var dateFormat = document.getElementById("dateFormatId").value;
        document.getElementById("startDateId").value =getCurrentDate(dateFormat);
        document.getElementById("endDateId").value =getCurrentDate(dateFormat);
        document.getElementById("searchpannell").style.width="440px";
      } else {
        document.getElementById("errorpickers").style.display="none";
        dpks.style.display = 'none';
        document.getElementById("searchpannell").style.width="120px";

      }
    }



