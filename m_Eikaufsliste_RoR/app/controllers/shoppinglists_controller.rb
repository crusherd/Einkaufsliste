class ShoppinglistsController < ApplicationController
  # GET /shoppinglists
  # GET /shoppinglists.json
  def index
    @current_user = User.find_by_id(session[:user_id])
    if @current_user.nil?
       @shoppinglists = Shoppinglist.all
    else
       @shoppinglists = @current_user.shoppinglists   
    end

    respond_to do |format|
       format.html # index.html.erb
       format.json { render json: @shoppinglists }
    end
  end

  # GET /shoppinglists/1
  # GET /shoppinglists/1.json
  def show
    @shoppinglist = Shoppinglist.find(params[:id])

    respond_to do |format|
      format.html # show.html.erb
      format.json { render json: @shoppinglist }
    end
  end

  # GET /shoppinglists/new
  # GET /shoppinglists/new.json
  def new
    @shoppinglist = Shoppinglist.new
    @current_user = User.find_by_id(session[:user_id])

    respond_to do |format|
      format.html # new.html.erb
      format.json { render json: @shoppinglist }
    end
  end

  # GET /shoppinglists/1/edit
  def edit
    @shoppinglist = Shoppinglist.find(params[:id])
  end

  # POST /shoppinglists
  # POST /shoppinglists.json
  def create
    @shoppinglist = Shoppinglist.new(params[:shoppinglist])
    @current_user = User.find_by_id(session[:user_id])
    if !@current_user.nil?
      @shoppinglist.user_id = @current_user.id
    end
    
    respond_to do |format|
      if @shoppinglist.save
        format.html { redirect_to @shoppinglist, notice: 'Shoppinglist was successfully created.' }
        format.json { render json: @shoppinglist, status: :created, location: @shoppinglist }
      else
        format.html { render action: "new" }
        format.json { render json: @shoppinglist.errors, status: :unprocessable_entity }
      end
    end
  end

  # PUT /shoppinglists/1
  # PUT /shoppinglists/1.json
  def update
    @shoppinglist = Shoppinglist.find(params[:id])
    @current_user = User.find_by_id(session[:user_id])
    if !@current_user.nil?
      @shoppinglist.user_id = @current_user.id
    end

    respond_to do |format|
      if @shoppinglist.update_attributes(params[:shoppinglist])
        format.html { redirect_to @shoppinglist, notice: 'Shoppinglist was successfully updated.' }
        format.json { head :no_content }
      else
        format.html { render action: "edit" }
        format.json { render json: @shoppinglist.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /shoppinglists/1
  # DELETE /shoppinglists/1.json
  def destroy
    @shoppinglist = Shoppinglist.find(params[:id])
    @shoppinglist.destroy

    respond_to do |format|
      format.html { redirect_to shoppinglists_url }
      format.json { head :no_content }
    end
  end
  
  def select
    @shoppinglist = Shoppinglist.find(params[:id])
    session[:shoppinglist_id] = @shoppinglist.id
    redirect_to listings_path
  end
end
