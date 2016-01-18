$(function() {
  
  $('.tip').tooltip();
  
  $('.show-in-modal').click(function(e){
    $('#modal-show .img-content').html('<img class="img-responsive img-rounded" src="'+$(this).attr('src')+'" />');
    $('#modal-show').modal('show');
    e.preventDefault();
  });
  $(".about-tab-menu .list-group-item").click(function(e) {
      e.preventDefault();
      $(this).siblings('a.active').removeClass("active");
      $(this).addClass("active");
      var index = $(this).index();
      $("div.about-tab>div.about-tab-content").removeClass("active");
      $("div.about-tab>div.about-tab-content").eq(index).addClass("active");
  });
});

$(document).ready(
		function() {
			$('#wizard-post-photo').hide();
			$("#wizard-post-photo-file").change(
					function() {
						if (this.files && this.files[0]) {
							var reader = new FileReader();

							reader.onload = function(e) {
								$("#share-type").val("2");
								$('#wizard-post-photo').attr("src",e.target.result).fadeIn('slow');
								$("#save-YouTube-hidden").val("");
								$("#save-YouTube-iframe").hide();		    							
								$('#wizard-post-photo').show();
							}
							reader.readAsDataURL(this.files[0]);
						}
					});
			
    			$("#save-YouTube-btn").click(
    					function() {
    						var youtubelink = $("#save-YouTube-text").val();
    						if (youtubelink!="") {		    							
							    var results = new RegExp('[\?&]v=([^&#]*)').exec(youtubelink);
							    if (results==null){
	    							$('#myModal').modal('hide')
	    							return;
							    }
							    else{
							    	youtubelink = results[1] || 0;
    								$("#share-type").val("3");
	    							$("#save-YouTube-hidden").val(youtubelink);
	    							$("#save-YouTube-iframe").attr("src","https://www.youtube.com/embed/"+youtubelink).fadeIn('slow');
	    							
	    							$('#wizard-post-photo').hide();
	    							
	    							$("#save-YouTube-iframe").show();
	    							$('#myModal').modal('hide')
	    							return;
							    }
    						}
    					});
		});

$(document).ready(
		function() {
			$('#wizard-cover-photo-btn-save').hide();
			$('#wizard-profile-photo-btn-save').hide();
			$("#wizard-cover-photo-file").change(
					function() {
						if (this.files && this.files[0]) {
							var reader = new FileReader();

							reader.onload = function(e) {
								$('#wizard-cover-photo').css(
										'background-image',
										'url(' + e.target.result + ')').fadeIn(
										'slow');
								$('#wizard-cover-photo-btn-save').show();
								$('#wizard-cover-photo-btn-save').css(
										'opacity', '1');
							}
							reader.readAsDataURL(this.files[0]);
						}
					});

		    $("#wizard-profile-photo-file").change(
					function() {
						if (this.files && this.files[0]) {
							var reader = new FileReader();

							reader.onload = function(e) {
								$('#wizard-profile-photo').css(
										'background-image',
										'url(' + e.target.result + ')').fadeIn(
										'slow');
								$('#wizard-profile-photo-btn-save').show();
								$('#wizard-profile-photo-btn-save').css(
										'opacity', '1');
							}
							reader.readAsDataURL(this.files[0]);
						}
					});
		});

function dolike(postid,likedislike) {
	//alert(postid);
	req = initRequest();
	req.open("GET", "LikeDislikePost?postid=" + postid + "&likedislike="+likedislike,
			true);

	req.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
	req.onreadystatechange = function() {
		if (req.readyState == 4) {
			if (req.status == 200) {
				likebutton = document.getElementById("like_" + postid);
				dislikebutton = document.getElementById("dislike_" + postid);
				var responseXML = req.responseXML;
				if (responseXML == null) {
					return false;
				} else {
					var like = responseXML.getElementsByTagName("like")[0];
					var likecount = like.getElementsByTagName("likecount")[0].childNodes[0].nodeValue;
					var liked = like.getElementsByTagName("liked")[0].childNodes[0].nodeValue=="true";
					var dislikecount = like.getElementsByTagName("dislikecount")[0].childNodes[0].nodeValue;
					var disliked = like.getElementsByTagName("disliked")[0].childNodes[0].nodeValue=="true";
					
					if(liked) {
						likebutton.setAttribute("style", "color: blue");
					} else {
						likebutton.removeAttribute("style");
					}
					if(disliked) {
						dislikebutton.setAttribute("style", "color: blue");
					} else {
						dislikebutton.removeAttribute("style");
					}
					likebutton.innerHTML = "<i class='fa fa-thumbs-up icon'></i> "
							+ likecount;
					dislikebutton.innerHTML = "<i class='fa fa-thumbs-down icon'></i> "
						+ dislikecount;
				}
			}
		}
	};
	req.send(null);
}

function docomment(form){
	var postid = form.postid.value;
	var comment = form.comment.value;
	req = initRequest();
	req.open("GET", "CommentPost?postid=" + postid + "&comment="+comment,
			true);

	req.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
	req.onreadystatechange = function() {
		if (req.readyState == 4) {
			if (req.status == 200) {
				var responseXML = req.responseXML;
				if (responseXML == null) {
					return false;
				} else {
					form.comment.blur();
					form.comment.value="";
					var comment = responseXML.getElementsByTagName("comment")[0];
					var userid = comment.getElementsByTagName("userid")[0].childNodes[0].nodeValue;
					var image = comment.getElementsByTagName("image")[0].childNodes[0].nodeValue;
					var name = comment.getElementsByTagName("name")[0].childNodes[0].nodeValue;
					var time = comment.getElementsByTagName("time")[0].childNodes[0].nodeValue;
					var text = comment.getElementsByTagName("text")[0].childNodes[0].nodeValue;
					
					commentul = document.getElementById("comment_" + postid);
										
					var li = document.createElement("li");
					li.setAttribute("class", "comment animated fadeIn");
					li.innerHTML = "<a class='pull-left' href='#'> <img "+
									"class='avatar'"+
									"style=\"background-image: url('img/Profile/"+image+"'),url('img/Profile/default-avatar.png'); background-size: cover;\"> "+
							"</a> "+
								"<div class='comment-body'> "+
									"<div class='comment-heading'> "+
										"<h4 class='comment-user-name'> "+
											"<a href='Timeline?userid="+userid+"'>"+name+"</a> "+
										"</h4> "+
										"<h5 class='time'>"+time+"</h5> "+
									"</div> "+
									"<p style='margin-top:3px;'>"+text+"</p> "+
								"</div>";
					
					commentul.appendChild(li);
					
				}
			}
		}
	};
	req.send(null);
}

var autofield;
var completeTable;
var autoRow;
function init(){
	autofield = document.getElementById("autocompletefield");
	completeTable = document.getElementById("complete-table");
	autoRow = document.getElementById("auto-row");
}

function doCompletion(){
	req = initRequest();
	req.open("GET", "SearchResult?autofield=" + escape(autofield.value),
			true);
	req.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
	req.onreadystatechange = function() {
		if (completeTable.getElementsByTagName("a").length > 0) {
			completeTable.style.display = 'none';
			for (loop = completeTable.childNodes.length - 1; loop >= 0; loop--) {
				completeTable.removeChild(completeTable.childNodes[loop]);
			}
		}
		if (req.readyState == 4) {
			if (req.status == 200) {
				var responseXML = req.responseXML;
				if (responseXML == null) {
					return false;
				} else {
					var users = responseXML.getElementsByTagName("users")[0];
						
					//console.log(users.childNodes.length);
					if (users.childNodes.length > 0) {
						for (loop = 0; loop < users.childNodes.length; loop++) {
							var user = users.childNodes[loop];
							var userid = user.getElementsByTagName("userid")[0].childNodes[0].nodeValue;
							var fname = user.getElementsByTagName("fname")[0].childNodes[0].nodeValue;
							var lname = user.getElementsByTagName("lname")[0].childNodes[0].nodeValue;
							var image = user.getElementsByTagName("image")[0].childNodes[0].nodeValue;
							
							//console.log(loop+"."+fname+" : "+lname+" | "+userid);
							
							
							var row;
							var cell;
							var linkElement;

							
							completeTable.style.display = 'table';
							var row1 = document.createElement("tr");
							row = document.createElement("a");
							cell = document.createElement("td");
							cell2 = document.createElement("td");
							cell.appendChild(row);
							row1.appendChild(cell2);
							row1.appendChild(cell);
							completeTable.appendChild(row1);
							
							row1.className = "popupItem";
							row.setAttribute("href", "Timeline?userid=" + userid);
							row.setAttribute("style", "display: block;");
							row.appendChild(document.createTextNode(fname + " " + lname));
							cell2.setAttribute("style","padding:3px;vertical-align: middle; width: 45px;");
							var imageElement = document.createElement("img");
							imageElement.setAttribute("style", "background-image: url('img/Profile/"+image+"'),url('img/Profile/default-avatar.png'); background-size: cover;");
							imageElement.setAttribute("width", "39px");
							imageElement.setAttribute("height", "39px");
							cell2.appendChild(imageElement);
							
							
						}
					}
					

					
					
				}
			}
		}
	};
	req.send(null);
}



function initRequest() {
	if (window.XMLHttpRequest) {
		if (navigator.userAgent.indexOf('MSIE') != -1) {
			isIE = true;
		}
		return new XMLHttpRequest();
	} else if (window.ActiveXObject) {
		isIE = true;
		return new ActiveXObject("Microsoft.XMLHTTP");
	}
}

$(document).ready(function(){
    $("#wizard-picture").change(function(){
        readURL(this);
    });
    
    
    $('[data-toggle="wizard-radio"]').click(function(event){
        wizard = $(this).closest('.wizard-card');
        wizard.find('[data-toggle="wizard-radio"]').removeClass('active');
        $(this).addClass('active');
        $(wizard).find('[type="radio"]').removeAttr('checked');
        $(this).find('[type="radio"]').attr('checked','true');
    });
    
    $height = $(document).height();
    
    
});


function readURL(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            $('#wizardPicturePreview').attr('src', e.target.result).fadeIn('slow');
        }
        reader.readAsDataURL(input.files[0]);
    }
}
