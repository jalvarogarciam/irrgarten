module Irrgarten
  class Dice
    # Constantes
    @@MAX_USES = 5
    @@MAX_INTELLIGENCE = 10.0
    @@MAX_STRENGTH = 10.0
    @@RESURRECT_PROB = 0.3
    @@WEAPONS_REWARD = 2
    @@SHIELDS_REWARD = 3
    @@HEALTH_REWARD = 5
    @@MAX_ATTACK = 3
    @@MAX_SHIELD = 2

    def self.MAX_USES
      @@MAX_USES
    end
    def self.MAX_INTELLIGENCE
      @@MAX_INTELLIGENCE
    end
    def self.MAX_STRENGTH
      @@MAX_STRENGTH
    end
    def self.RESURRECT_PROB
      @@RESURRECT_PROB
    end
    def self.WEAPONS_REWARD
      @@WEAPONS_REWARD
    end
    def self.SHIELDS_REWARD
      @@SHIELDS_REWARD
    end
    def self.HEALTH_REWARD
      @@HEALTH_REWARD
    end
    def self.MAX_ATTACK
      @@MAX_ATTACK
    end
    def self.MAX_SHIELD
      @@MAX_SHIELD
    end
    # Método para devolver un número aleatorio entre 0 y max - 1
    def self.random_pos(max)
      rand(max)  # Devuelve un número entre 0 y max - 1
    end

    # Método para devolver el índice del jugador que comenzará
    def self.who_starts(nplayers)
      rand(nplayers)  # Devuelve un número entre 0 y nplayers - 1
    end

    # Método para devolver un valor aleatorio de inteligencia
    def self.random_intelligence
      rand * @@MAX_INTELLIGENCE  # Devuelve un valor aleatorio de inteligencia en [0, MAX_INTELLIGENCE]
    end

    # Método para devolver un valor aleatorio de fuerza
    def self.random_strength
      rand * @@MAX_STRENGTH  # Devuelve un valor aleatorio de fuerza
    end

    # Método para indicar si un jugador muerto debe ser resucitado
    def self.resurrect_player
      rand <= @@RESURRECT_PROB  # Devuelve true si se resucita al jugador
    end

    # Método para indicar la cantidad de armas recibidas
    def self.weapons_reward
      rand(@@WEAPONS_REWARD + 1)  # Cantidad de armas recibidas
    end

    # Método para indicar la cantidad de escudos recibidos
    def self.shields_reward
      rand(@@SHIELDS_REWARD + 1)  # Cantidad de escudos recibidas
    end

    # Método para indicar la cantidad de salud recibida
    def self.health_reward
      rand(@@HEALTH_REWARD + 1)  # Cantidad de unidades de salud recibidas
    end

    # Método para devolver un valor aleatorio de potencia de arma
    def self.weapon_power
      rand * @@MAX_ATTACK  # Devuelve un valor aleatorio en el intervalo [0, MAX_ATTACK]
    end

    # Método para devolver un valor aleatorio de potencia de escudo
    def self.shield_power
      rand * @@MAX_SHIELD  # Devuelve un valor aleatorio en el intervalo [0, MAX_SHIELD]
    end

    # Método para devolver el número de usos aleatorio
    def self.uses_left
      rand(@@MAX_USES + 1)  # Devuelve el número de usos aleatorio
    end

    # Método para devolver la cantidad aleatoria de competencia aplicada
    def self.intensity(competence)
      rand * competence  # Cantidad de competencia aplicada
    end

    # Método para determinar si un elemento debe ser descartado
    def self.discard_element(uses_left)
      return true if uses_left == 0
      return false if uses_left == @@MAX_USES
      rand > (uses_left.to_f / @@MAX_USES)  # Probabilidad inversamente proporcional
    end


  end
end

