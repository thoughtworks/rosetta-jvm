require 'java'
require 'rosetta/sinatra'
require 'jackson/json'

describe "Rosetta::Sinatra" do
  require 'rack/test'

  include Rack::Test::Methods

  def app
    app = Rosetta::Sinatra
    app.set :environment, :test
    app
  end

  it "has a rails project /projects/rails" do
    header "HTTP_ACCEPT", "application/json"

    get '/projects/rails'

    last_response.should be_ok
    JSON.parse(last_response.body.join).should == {"name" => "rails"}
  end

  it "doesn't have a blah project /projects/blah" do
    get '/projects/blah'
    last_response.should be_not_found
  end
end
