class StoresController < ApplicationController
  # GET /stores
  # GET /stores.json
  def index
    @stores = Store.all

    respond_to do |format|
      format.html # index.html.erb
      format.json { render json: @stores, :include => :addresses }
    end
  end

  # GET /stores/1
  # GET /stores/1.json
  def show
    @store = Store.find(params[:id])

    respond_to do |format|
      format.html # show.html.erb
      format.json { render json: @store }
    end
  end

  # GET /stores/new
  # GET /stores/new.json
  def new
    @store = Store.new

    respond_to do |format|
      format.html # new.html.erb
      format.json { render json: @store }
    end
  end

  # GET /stores/1/edit
  def edit
    @store = Store.find(params[:id])
  end

  # POST /stores
  # POST /stores.json
  def create
    @store = Store.new(params[:store])
    @mapped_address = Address.find_by_id(params[:address_id])
    
    # if address is not found (nil), nothing was selected by the selectbox
    if @mapped_address.nil? 
      # check if user want to assign the store with a new address
      if params[:street] != ""
        if params[:zipcode] != ""
          if params[:city] != ""
            @new_address = Address.new(:city => params[:city], :street => params[:street], :zipcode => params[:zipcode])
            @new_address.save
            @store.addresses << @new_address
          end
        end
      end
    else
      # can assign the selected address to store
      @store.addresses << @mapped_address
    end

    respond_to do |format|
      if @store.save
        format.html { redirect_to @store, notice: 'Store was successfully created.' }
        format.json { render json: @store, status: :created, location: @store }
      else
        format.html { render action: "new" }
        format.json { render json: @store.errors, status: :unprocessable_entity }
      end
    end
  end

  # PUT /stores/1
  # PUT /stores/1.json
  def update
    @store = Store.find(params[:id])

    respond_to do |format|
      if @store.update_attributes(params[:store])
        format.html { redirect_to @store, notice: 'Store was successfully updated.' }
        format.json { head :no_content }
      else
        format.html { render action: "edit" }
        format.json { render json: @store.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /stores/1
  # DELETE /stores/1.json
  def destroy
    @store = Store.find(params[:id])
    @store.destroy

    respond_to do |format|
      format.html { redirect_to stores_url }
      format.json { head :no_content }
    end
  end
  
  
  def add_address
    @current_store = Store.find(params[:id])
    redirect_to new_address_ref_path(@current_store)
  end
    
  def delete_address_ref
    @store = Store.find(params[:id])
    @address = Address.find(params[:address_id])
    
    @store.addresses.delete(@address)    
    
    respond_to do |format|
      format.html { redirect_to stores_url }
      format.json { head :no_content }
    end
  end
  
  def delete_article_ref
    @store = Store.find(params[:id])
    @article = Article.find(params[:article_id])
    
    @store.articles.delete(@article)
    
    respond_to do |format|
      format.html { redirect_to stores_url }
      format.json { head :no_content }
    end
  end
end
