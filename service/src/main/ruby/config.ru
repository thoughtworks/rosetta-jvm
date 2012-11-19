require 'rubygems'

require 'stdlib/patches/pathname'

require 'rack'

require 'sprockets'
require 'coffee-script'

require 'rosetta/routing'

run Rack::Builder.new {
  map "/assets" do
    asset_root = java.lang.System.getProperty("asset.root") or raise "System.getProperty(\"asset.root\") undefined."

    environment = Sprockets::Environment.new asset_root
    environment.append_path '.'

    run environment
  end

  map "/" do
    run Rosetta::Routing
  end
}
