class PurchaseHistoriesController < ApplicationController
  # GET /purchase_histories
  # GET /purchase_histories.json
  def index
    @purchase_histories = PurchaseHistory.all

    respond_to do |format|
      format.html # index.html.erb
      format.json { render json: @purchase_histories }
    end
  end

  # GET /purchase_histories/1
  # GET /purchase_histories/1.json
  def show
    @purchase_history = PurchaseHistory.find(params[:id])

    respond_to do |format|
      format.html # show.html.erb
      format.json { render json: @purchase_history }
    end
  end

  # GET /purchase_histories/new
  # GET /purchase_histories/new.json
  def new
    @purchase_history = PurchaseHistory.new

    respond_to do |format|
      format.html # new.html.erb
      format.json { render json: @purchase_history }
    end
  end

  # GET /purchase_histories/1/edit
  def edit
    @purchase_history = PurchaseHistory.find(params[:id])
  end

  # POST /purchase_histories
  # POST /purchase_histories.json
  def create
    @purchase_history = PurchaseHistory.new(params[:purchase_history])

    respond_to do |format|
      if @purchase_history.save
        format.html { redirect_to @purchase_history, notice: 'Purchase history was successfully created.' }
        format.json { render json: @purchase_history, status: :created, location: @purchase_history }
      else
        format.html { render action: "new" }
        format.json { render json: @purchase_history.errors, status: :unprocessable_entity }
      end
    end
  end

  # PUT /purchase_histories/1
  # PUT /purchase_histories/1.json
  def update
    @purchase_history = PurchaseHistory.find(params[:id])

    respond_to do |format|
      if @purchase_history.update_attributes(params[:purchase_history])
        format.html { redirect_to @purchase_history, notice: 'Purchase history was successfully updated.' }
        format.json { head :no_content }
      else
        format.html { render action: "edit" }
        format.json { render json: @purchase_history.errors, status: :unprocessable_entity }
      end
    end
  end

  # DELETE /purchase_histories/1
  # DELETE /purchase_histories/1.json
  def destroy
    @purchase_history = PurchaseHistory.find(params[:id])
    @purchase_history.destroy

    respond_to do |format|
      format.html { redirect_to purchase_histories_url }
      format.json { head :no_content }
    end
  end
end
