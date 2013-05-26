package ar.com.sociability

import org.springframework.web.multipart.MultipartRequest
import org.springframework.web.multipart.MultipartFile

class UploadController {

    def index() { 
		
		redirect action: 'upload'
	}
	
	def upload = {
		def file = request.getFile('file')
	
		session.file = [
			bytes: file.inputStream.bytes,
			contentType: file.contentType
		]
	
		redirect action: 'elsewhere'
	}
	
	def elsewhere = { }
	
	def image = {
		if (!session.file) {
			response.sendError(404)
			return
		}
	
		def file = session.file
		session.removeAttribute 'file'
	
		response.setHeader('Cache-Control', 'no-cache')
		response.contentType = file.contentType
		response.outputStream << file.bytes
		response.outputStream.flush()
	
	}
}
