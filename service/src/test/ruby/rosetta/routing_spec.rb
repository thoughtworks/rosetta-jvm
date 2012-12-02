require 'java'
require 'json'
require 'rosetta/routing'

describe "Rosetta::Routing" do
  require 'rack/test'

  include Rack::Test::Methods

  def app
    app = Rosetta::Routing
    app.set :environment, :test
    app.new(Java::RosettaService::StubRepositoryModule.new)
  end

  it "has a rails project /projects/rails/rails" do
    header "HTTP_ACCEPT", "application/json"

    get '/projects/rails/rails'

    last_response.should be_ok

    JSON.parse(last_response.body.join).should == {
        "user" => "rails",
        "repository" => "rails",
        "languages" => [
          {"name" => "ruby", "weighting" => 60},
          {"name" => "python", "weighting" => 10},
          {"name" => "javascript", "weighting" => 15},
          {"name" => "clojure", "weighting" => 15},
        ]
    }
  end

  it "doesn't have a blah project /projects/blah/blah" do
    get '/projects/blah/blah'
    last_response.should be_not_found
  end

  it "has an application health page /application/health" do
    header "HTTP_ACCEPT", "application/json"

    get '/application/health'

    last_response.should be_ok
    JSON.parse(last_response.body.join).should == {"status" => "up"}
  end
end
