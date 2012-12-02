require 'sinatra/base'
require 'sinatra/respond_with'

require 'rosetta/resources'
require 'rosetta/handler'

module Rosetta
  class Routing < Sinatra::Base
    register Sinatra::RespondWith

    def initialize repository_module = Java::RosettaService::LiveRepositoryModule.new
      @injector = Java::ComGoogleInject::Guice.create_injector(repository_module)
      @projects = @injector.get_instance Java::RosettaService::Projects.java_class
    end

    get('/ping') {}
    get('/projects/:user/:repository') { |user, repository| Resources::ProjectsResource.new(@projects, self).show(user, repository) }
    get('/application/health') { Resources::HealthResource.new.show }
  end
end
