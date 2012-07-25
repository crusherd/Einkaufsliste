class WelcomeController < ActionController::Base
  require 'socket'
  
  def index
    # turn off reverse DNS resolution temporarily
    @local_ip = UDPSocket.open {|s| s.connect("192.0.0.1", 1); s.addr.last}
    #@local_ip = UDPSocket.open {|s| s.connect("127.0.0.1", 1); s.addr.last}
    #TODO: failback mode if no connection is available
    session.clear
    
    respond_to do |format|
      format.html # index.html.erb
    end
  end
end
