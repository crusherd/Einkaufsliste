class ListingsController < ApplicationController
  # GET /listings
  # GET /listings.json
  def index
    @current_list = Shoppinglist.find_by_id(session[:shoppinglist_id])
    if @current_list.nil?
       @listings = Listing.all
    else
       @listings = Listing.where(:shoppinglist_id => @current_list.id).all
    end
    
    respond_to do |format|
      format.html # index.html.erb
	    format.json { render :json => @listings}
    end
  end
  
  # DELETE /shoppinglists/1
  # DELETE /shoppinglists/1.json
  def destroy
    @listing = Listing.find(params[:id])
    @listing.destroy

    respond_to do |format|
      format.html { redirect_to listings_url }
      format.json { head :no_content }
    end
  end
  
  def add
    @current_list = Shoppinglist.find_by_id(session[:shoppinglist_id])
    # find if an entry allready exist 
    entry = Listing.find(:first, :conditions => { :article_id => params[:article_id], :shoppinglist_id => @current_list.id })
    
    if entry.nil?
      # no entry so create a new
      @listing = Listing.new(:amount => params[:amount], :article_id => params[:article_id], :shoppinglist_id => @current_list.id)
    else
      # entry found, so just increment amount
      @listing = entry
      @listing.amount += params[:amount].to_i 
    end
    
    respond_to do |format|
      if @listing.save        
        format.html {redirect_to listings_path, notice: 'Article was successfully add to' + @current_list.name + '.' }
        format.json { render :json => @listings }
      else
        format.html { render action: "index"}
        format.json { render json: @listing.errors, status: :unprocessable_entity }
      end
    end
  end
  
  def create
    @current_list = Shoppinglist.find_by_id(session[:shoppinglist_id])
    if !@current_list.nil?
      price = params[:price]
      if price.empty?
        @article = Article.new(:name => params[:name], :price => 0)
      else
        @article = Article.new(:name => params[:name], :price => params[:price])
      end 
      
      @article.save
      @listing = Listing.new(:amount => params[:amount], :article_id => @article.id, :shoppinglist_id => @current_list.id)
    end
    
    respond_to do |format|
      if @listing.save
        format.html {redirect_to listings_path, notice: 'Article was successfully created for' + @current_list.name + '.' }
        format.json { render :json => @listings}
      else
        format.html { render action: "index" }
        format.json { render json: @listing.errors, status: :unprocessable_entity }
      end
    end
  end
end
