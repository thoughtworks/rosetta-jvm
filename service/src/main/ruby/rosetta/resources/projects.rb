module Rosetta
  module Resources
    class ProjectsResource
      def initialize projects, responder
        @projects = projects
        @responder = responder
      end

      def show id
        @projects.find id, Handler.new { |h|
          h.found do |project|
            @responder.respond_to do |f|
              f.json { project.to_json }
            end
          end

          h.not_found do
            @responder.status 404
          end
        }
      end
    end
  end
end
