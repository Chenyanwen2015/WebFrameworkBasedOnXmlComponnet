$(document).ready(function(){

});

var aJaxClick = function(e){

} ;

var setTable = function(e){

} ;

var ajaxFormFunction = function(e,url){
   //console.log(e.parent);
   //console.log($(e).parent());
   /*$(e).parent().submit(function(temp){
      console.log(temp);
      alert("Submitted");
   });
   $(e).parent().submit();*/
   var str="";
   $(e).parent().children().each(function(i,n){
      var obj = $(n);
      if(obj.attr("name") !=null && obj.attr("name") !=""){
         var value = obj.val();
         if(value ==null)
             value = " ";
            str+=obj.attr("name")+"="+value+"&";
      }
   });
   $.ajax({
      type: 'POST',
      url: url,
      data: {fString:str,business:$(e).attr("name")},
      success: function(data){
         console.log(data);
         if(data.success == true){
            if(data.type=="redirect"){
               window.location.href=data.or;
            }
            else if(data.type == "html"){
               alert(data.or);
            }
         }
      },
      complete: function(XMLHttpRequest, textStatus){

      },
      //调用出错执行的函数
      error: function(XMLHttpRequest, textStatus){
         alert(XMLHttpRequest.responseText+":"+textStatus);
      },
      dataType: "json"
   });
};