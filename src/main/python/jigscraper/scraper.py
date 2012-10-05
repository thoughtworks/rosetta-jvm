import re
import os
import urllib
import urllib2
import cookielib

cookieJar = cookielib.LWPCookieJar()
if os.path.isfile("cookie"): 
  cookieJar.load("cookie")

urlopener=urllib2.build_opener(urllib2.HTTPCookieProcessor(cookieJar))
urlopener.addheaders=[('user-agent',"Mozilla/5.0")]

def reload():
  execfile(__file__,globals())

def getpage(url, POST=None):
  if POST: POST=urllib.urlencode(POST)
  return urlopener.open(url, POST).read()

def getinputs(pagebody):
  print "type\t\tname\t\tdefault value\n","-"*50
  results={}

  for input in re.findall("<input .+?>", pagebody, re.S):
    attributematches = []
    attributematches.append(re.search('type=[\'"](.*?)[\'"]', input))
    attributematches.append(re.search('name=[\'"](.*?)[\'"]', input))
    attributematches.append(re.search('value=[\'"](.*?)[\'"]', input))
    print "%s\t\t%s\t\t%s"%tuple((attributematch.groups()[0] if attributematch else "" for attributematch in attributematches))
    if attributematches[1]:
      results[attributematches[1].groups()[0]] = attributematches[2].groups()[0] if attributematches[2] else ""

  for input in re.findall("<textarea .+?>", pagebody, re.S):
    attributematches = []
    attributematches.append(re.search('type=[\'"](.*?)[\'"]', input))
    attributematches.append(re.search('name=[\'"](.*?)[\'"]', input))
    attributematches.append(re.search('value=[\'"](.*?)[\'"]', input))

    print "%s\t\t%s\t\t%s"%tuple((attributematch.groups()[0] if attributematch else "" for attributematch in attributematches))

    if attributematches[1]:
      results[attributematches[1].groups()[0]] = attributematches[2].groups()[0] if attributematches[2] else ""

  return results

def betterinputs(something):
  while True:
    pass

def getforms(pagebody):
  print "id\t\taction\t\tonsubmit\n","-"*50
  for forms in re.findall("<form .+?>", pagebody, re.S):
    attributematches = []
    attributematches.append(re.search('id="(.+?)"', forms))
    attributematches.append(re.search('action="(.+?)"', forms))
    attributematches.append(re.search('onsubmit="(.+?)"', forms))
    print "%s\t\t%s\t\t%s"%tuple((attributematch.groups()[0] if attributematch else "" for attributematch in attributematches))

def login(username, passtoken):
  page = getpage("https://jigsaw.thoughtworks.com/")
  if "<title>Jigsaw" in page:
    return
  
  inputs = getinputs(page)
  inputs["username"] = username
  inputs["password"] = passtoken

  getpage("https://cas.thoughtworks.com/cas/login?service=https%3A%2F%2Fjigsaw.thoughtworks.com%2F", inputs)

  cookieJar.save("cookie")

def profilePictureFor(id):
  page = getpage("https://jigsaw.thoughtworks.com/consultants/" + str(id))
  url = re.search(r"/consultants/\d+/show_picture.+?&", page).group()
  return getpage("https://jigsaw.thoughtworks.com" + url)


if __name__=="__main__":
  import sys
  login(sys.argv[1], sys.argv[2])
  open("picture.jpg", "w").write(profilePictureFor(sys.argv[3]))
