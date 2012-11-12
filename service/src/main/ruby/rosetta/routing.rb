require 'sinatra/base'
require 'sinatra/respond_with'

require 'rosetta/resources'
require 'rosetta/handler'

module Rosetta
  class Routing < Sinatra::Base
    register Sinatra::RespondWith

    def initialize
      @injector = Java::ComGoogleInject::Guice.create_injector(Java::RosettaService::RepositoryModule.new)
      @projects = @injector.get_instance Java::RosettaService::Projects.java_class
    end

    get('/ping') {}
    get('/projects/:id') { |id| Resources::ProjectsResource.new(@projects, self).show(id) }
    get('/application/health') { Resources::HealthResource.new.show }
  end
end
