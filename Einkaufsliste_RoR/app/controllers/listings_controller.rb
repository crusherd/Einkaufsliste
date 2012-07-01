class ListingsController < ApplicationController
  
  # GET /articles
  # GET /articles.json
  # does not work yet
  def index
    @current_shoppinglist = Shoppinglist.find_by_id(session[:shoppinglist_id])
    if @current_shoppinglist.nil?
      @listings = Article.all
    else
      @listings = @current_shoppinglist.
    end

    respond_to do |format|
      format.html # index.html.erb
      format.json { render json: @articles }
    end
  end
end