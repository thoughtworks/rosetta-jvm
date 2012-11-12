require 'sinatra/base'
require 'sinatra/respond_with'

require 'rosetta/handler'

module Rosetta
  class Routing < Sinatra::Base
    register Sinatra::RespondWith

    def initialize
      @injector = Java::ComGoogleInject::Guice.create_injector(Java::RosettaService::RepositoryModule.new)
      @projects = @injector.get_instance Java::RosettaService::Projects.java_class
    end

    get '/ping' do
    end

    get '/projects/:id' do
      @projects.find params[:id], Handler.new do |h|
        h.found do |project|
          respond_to do |f|
            f.json { project.to_json }
          end
        end

        h.not_found do
          status 404
        end
      end
    end
  end
end
