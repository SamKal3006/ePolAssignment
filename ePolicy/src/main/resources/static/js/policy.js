$( window ).on( "load", function() {
	    fillPolicyRadio();
  		fillPremiumAmount();
  		
  		$("#commissionAmount").change(function(){
  			fillPremiumAmount();
  		});
  		
  		$('input[type=radio][name="policy"]').change(function() {
  			fillPremiumAmount();
  		});
  		
  		$("#premiumPaymentPlan").change(function(){
  			fillPremiumAmount();
  		});
  		
  		function fillPolicyRadio() {
  			if (!$('input:radio[name="policy"]').is(':checked')) {
  		    	$('input:radio[name="policy"][value="1"]').prop('checked', true);
  			}
  		}
  		
  		function fillPremiumAmount() {
  		  var paymentPlan = $("#premiumPaymentPlan :selected").val();
  		  var selectedPolicy = $("input[name='policy']:checked").val();
  		  var policyPremium = $("#policy"+selectedPolicy+"hidden").val();
  		  var pAmount;
  		  var cAmount=0;
  		  if(paymentPlan === 'MONTHLY') {
  			  pAmount = (policyPremium/12.00);
  		  }
  		  else if(paymentPlan === 'QUARTERLY') {
  			  pAmount = (policyPremium/4.00);
  		  }
  		  else if(paymentPlan === 'SEMI_ANNUALLY') {
  			  pAmount = (policyPremium/2.00);
  		  }
  		  else if(paymentPlan === 'ANNUALLY') {
			  pAmount = (policyPremium/1.00);
		  }
  		  if($("#commissionAmount").length > 0) {
  			  cAmount = ($("#commissionAmount").val())/1.00;
  		  }
  		  $("#premiumAmount").val(pAmount + cAmount);  		  
  		 
  		}
 });