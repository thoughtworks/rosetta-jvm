import SimpleHTTPServer
import SocketServer
import scraper

class MyRequestHandler(SimpleHTTPServer.SimpleHTTPRequestHandler):
  def do_GET(self):
    id = self.path[1:]
    print id
    self.send_response(200)
    self.send_header('Content-type', 'image/jpeg')
    self.end_headers()
    self.wfile.write(scraper.idToPic(id))
    return

scraper.login()
Handler = MyRequestHandler
server = SocketServer.TCPServer(('0.0.0.0', 8000), Handler)

server.serve_forever()

