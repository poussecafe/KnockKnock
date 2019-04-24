///**
// * 
// */
//var uploadFile = HttpContext.Current.Request.Files;
//
//var currentUploadFile = uploadFile[0];
//if (currentUploadFile != null && currentUploadFile.ContentLength > 0) {
//	var uploadFileName = Path.GetFileName(currentUploadFile.FileName);
//
//	var baseDomainAddress = "http://localhost:1235/adminEventWriteView?";
//	var fileUploadFolder = "../static/images/event";
//	var fileUploadFolderWebPath = baseDomainAddress + "/editor_upload";
//
//	if (Directory.Exists(fileUploadFolder) == false) {
//		Directory.CreateDirectory(fileUploadFolder);
//	}
//
//	var fileUploadAllowExtension = "jpg,png,jpeg";
//
//	var uniqueFileNameFullPath = GetUniqueFileName(fileUploadFolder,
//			uploadFileName);
//
//	var fileExtension = uniqueFileNameFullPath.Substring(
//			uniqueFileNameFullPath.LastIndexOf(".") + 1).ToLower();
//
//	var allowFileExtension = fileUploadAllowExtension.Split(',');
//
//	if (allowFileExtension.Contains(fileExtension) == true) {
//		currentUploadFile.SaveAs(uniqueFileNameFullPath);
//		var webPath = fileUploadFolderWebPath
//				+ Path.GetFileName(uniqueFileNameFullPath);
//
//		Response
//				.Write("<script type='text/javascript'>\nwindow.parent.CKEDITOR.tools.callFunction(1, '"
//						+ webPath + "', \');\n</script>");
//	} else {
//		Response
//				.Write("<script type='text/javascript'>\nalert('허용되지 않은 파일 유형입니다.');\n</script>");
//	}
//}