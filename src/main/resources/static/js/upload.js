$(document).ready(function() {
	$("#results").hide();
	$("#finalResult").hide();
    $("#upload-file-button").hide();
    $("#upload-file-input").on("change", allowUpload);
    $("#upload-file-button").on("click", upload);

});

function allowUpload() {
    $("#upload-file-button").show();
}

function upload() {
	$.ajax({
		url : "/upload",
		type : "POST",
		data : new FormData($("#upload-file-form")[0]),
		enctype : 'multipart/form-data',
		processData : false,
		contentType : false,
		cache : false,
		success : function() {
			setSuccessBadges();
		},
		error : function(data) {
			$("#results").show();
			$("#finalResultText").text("File was not successfully uploaded.");

			fileSizeBadge(data);
            fileExtensionBadge(data);
			contentTypeBadge(data);

			if (data.responseJSON.failed) {
				$("#file-uploaded").text("No");
				$("#file-uploaded").removeClass().addClass("badge badge-pill badge-danger");
			}
			$("#finalResult").removeClass().addClass("alert alert-danger");
			$("#finalResult").show();
		}
	});
}

function setSuccessBadges() {
    $("#results").show();
    $("#file-size").text("OK");
    $("#file-size").removeClass().addClass("badge badge-pill badge-success");
    $("#file-content-type").text("OK");
    $("#file-content-type").removeClass().addClass("badge badge-pill badge-success");
    $("#file-extension").text("OK");
    $("#file-extension").removeClass().addClass("badge badge-pill badge-success");
    $("#file-uploaded").text("OK");
    $("#file-uploaded").removeClass().addClass("badge badge-pill badge-success");
    $("#finalResultText").text("File was successfully uploaded.");
    $("#finalResult").removeClass().addClass("alert alert-success");
    $("#finalResult").show();
}

function fileSizeBadge(data) {
    if (data.responseJSON.validFileSize == undefined) {
        $("#file-size").text("OK");
        $("#file-size").removeClass().addClass("badge badge-pill badge-success");
    }
    else if (!data.responseJSON.validFileSize) {
        $("#file-size").text("ERROR");
        $("#file-size").removeClass().addClass("badge badge-pill badge-danger");
    }
}

function fileExtensionBadge(data) {
    if (data.responseJSON.validFileExtension == undefined) {
        $("#file-extension").text("?");
        $("#file-extension").removeClass().addClass("badge badge-pill badge-warning");
    } else if (data.responseJSON.validFileExtension) {
        $("#file-extension").text("OK");
        $("#file-extension").removeClass().addClass("badge badge-pill badge-success");
    } else {
        $("#file-extension").text("ERROR");
        $("#file-extension").removeClass().addClass("badge badge-pill badge-danger");
    }
}

function contentTypeBadge(data) {
    if (data.responseJSON.validContentType == undefined) {
        $("#file-content-type").text("?");
        $("#file-content-type").removeClass().addClass("badge badge-pill badge-warning");
    } else if (data.responseJSON.validContentType) {
        $("#file-content-type").text("OK");
        $("#file-content-type").removeClass().addClass("badge badge-pill badge-success");
    } else {
        $("#file-content-type").text("ERROR");
        $("#file-content-type").removeClass().addClass("badge badge-pill badge-danger");
    }
}