require 'java'
require 'json'
require 'rosetta/routing'

describe "Rosetta::Routing" do
  require 'rack/test'

  include Rack::Test::Methods

  def app
    app = Rosetta::Routing
    app.set :environment, :test
    app
  end

  it "has a rails project /projects/rails" do
    header "HTTP_ACCEPT", "application/json"

    get '/projects/rails'

    last_response.should be_ok

    JSON.parse(last_response.body.join).should == {"name" => "rails", "languages" => [
      {"name" => "ruby", "percentage" => 60},
      {"name" => "python", "percentage" => 10},
      {"name" => "javascript", "percentage" => 15},
      {"name" => "clojure", "percentage" => 15},
    ]}
  end

  it "doesn't have a blah project /projects/blah" do
    get '/projects/blah'
    last_response.should be_not_found
  end

  it "has an application health page /application/health" do
    header "HTTP_ACCEPT", "application/json"

    get '/application/health'

    last_response.should be_ok
    JSON.parse(last_response.body.join).should == {"status" => "up"}
  end
end
