module Irrgarten
  require_relative 'Dice'
  class Weapon
    # Atributos
    attr_reader :power, :uses

    # Constructor
    def initialize(power_, uses_)
      @power = power_
      @uses = uses_
    end

    # Método para realizar un ataque
    def attack
      if @uses > 0
        @uses -= 1  # Decrementa el uso
        @power  # Devuelve el poder del ataque
      else
        0  # Devuelve 0 si no hay usos disponibles
      end
    end

    # Método para decidir si el arma debe ser descartada
    def discard
      Dice.discard_element(@uses)  # Llama al método de la clase Dice
    end

    # Método para representar el arma como un string
    def to_s
      "W[#{@power}, #{@uses}]"  # Formato 'W[{power}, {uses}]'
    end

  end
end
