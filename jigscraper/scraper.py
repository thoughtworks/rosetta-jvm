import re
import os
import urllib
import urllib2
import cookielib

cookieJar = cookielib.LWPCookieJar()
if os.path.isfile("cookie"): 
  cookieJar.load("cookie")

o=urllib2.build_opener(urllib2.HTTPCookieProcessor(cookieJar))
o.addheaders=[('user-agent',"Mozilla/5.0")]

def reload():
  execfile(__file__,globals())

def getpage(url, POST=None):
  if POST: POST=urllib.urlencode(POST)
  return o.open(url, POST).read()

def getinputs(s):
  print "type\t\tname\t\tdefault value\n","-"*50
  d={}
  for i in re.findall("<input .+?>",s, re.S):
    l=[]
    l.append(re.search('type=[\'"](.*?)[\'"]', i))
    l.append(re.search('name=[\'"](.*?)[\'"]', i))
    l.append(re.search('value=[\'"](.*?)[\'"]', i))
    print "%s\t\t%s\t\t%s"%tuple((x.groups()[0] if x else "" for x in l))
    if l[1]: d[l[1].groups()[0]]=l[2].groups()[0] if l[2] else ""
  for i in re.findall("<textarea .+?>",s, re.S):
    l=[]
    l.append(re.search('type=[\'"](.*?)[\'"]', i))
    l.append(re.search('name=[\'"](.*?)[\'"]', i))
    l.append(re.search('value=[\'"](.*?)[\'"]', i))
    print "%s\t\t%s\t\t%s"%tuple((x.groups()[0] if x else "" for x in l))
    if l[1]: d[l[1].groups()[0]]=l[2].groups()[0] if l[2] else ""
  return d

def betterinputs(s):
  while True:
    pass

def getforms(s):
  print "id\t\taction\t\tonsubmit\n","-"*50
  for i in re.findall("<form .+?>",s, re.S):
    l=[]
    l.append(re.search('id="(.+?)"', i))
    l.append(re.search('action="(.+?)"', i))
    l.append(re.search('onsubmit="(.+?)"', i))
    print "%s\t\t%s\t\t%s"%tuple((x.groups()[0] if x else "" for x in l))

def login():
  p=getpage("https://jigsaw.thoughtworks.com/")
  if "<title>Jigsaw" in p  : return 
  
  i=getinputs(p)
  i["username"] = raw_input("your tw usar name: ")
  i["password"] = raw_input("your rsa token: ")
  getpage("https://cas.thoughtworks.com/cas/login?service=https%3A%2F%2Fjigsaw.thoughtworks.com%2F", i)
  cookieJar.save("cookie")

def idToPic(id):
  p=getpage("https://jigsaw.thoughtworks.com/consultants/"+str(id))
  url=re.search(r"/consultants/\d+/show_picture.+?&", p).group()
  return getpage("https://jigsaw.thoughtworks.com"+url)


if __name__=="__main__":
  import sys
  login()
  open("picture.jpg", "w").write(idToPic(sys.argv[1]))
