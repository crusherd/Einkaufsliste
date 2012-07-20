class WelcomeController < ActionController::Base
  require 'socket'
  
  def index
    # turn off reverse DNS resolution temporarily
    @local_ip = UDPSocket.open {|s| s.connect("192.0.0.1", 1); s.addr.last}
     
    session.clear 
    
    respond_to do |format|
      format.html # index.html.erb
    end
  end
end