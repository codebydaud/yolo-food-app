<template>
  <div v-if="isOpen" class="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50">
    <div class="bg-white rounded-lg shadow-lg p-8 w-full max-w-lg"> 
      <h2 class="text-2xl font-bold mb-6 text-center">Complete Your Profile</h2>
      <form @submit.prevent="submitProfile">
        <div class="mb-4">
          <label for="contact_number" class="block text-sm font-medium text-gray-700">Contact Number</label>
          <input
            v-model="form.contact_number"
            type="text"
            id="contact_number"
            maxlength="13"
            required
            class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
            @input="validateContactNumber"
          />
          <p v-if="errors.contact_number" class="text-red-500 text-sm mt-2">{{ errors.contact_number }}</p>
        </div>

        <div class="mb-4">
          <label for="house" class="block text-sm font-medium text-gray-700">House</label>
          <input
            v-model="form.house"
            type="text"
            id="house"
            maxlength="16"
            required
            class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
          />
        </div>

        <div class="mb-4">
          <label for="street" class="block text-sm font-medium text-gray-700">Street</label>
          <input
            v-model="form.street"
            type="text"
            id="street"
            maxlength="16"
            required
            class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
          />
        </div>

        <div class="mb-4">
          <label for="area" class="block text-sm font-medium text-gray-700">Area</label>
          <input
            v-model="form.area"
            type="text"
            id="area"
            maxlength="32"
            class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
          />
        </div>

        <div class="mb-4">
          <label for="zip_code" class="block text-sm font-medium text-gray-700">Zip Code</label>
          <input
            v-model="form.zip_code"
            type="text"
            id="zip_code"
            minlength="4"
            maxlength="5"
            required
            class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
            @input="validateZipCode"
          />
          <p v-if="errors.zip_code" class="text-red-500 text-sm mt-2">{{ errors.zip_code }}</p>
        </div>

        <div class="mb-4">
          <label for="city" class="block text-sm font-medium text-gray-700">City</label>
          <input
            v-model="form.city"
            type="text"
            id="city"
            maxlength="32"
            required
            class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
          />
        </div>

        <div class="mb-4">
          <label for="country" class="block text-sm font-medium text-gray-700">Country</label>
          <input
            v-model="form.country"
            type="text"
            id="country"
            maxlength="32"
            required
            class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
          />
        </div>

        <div class="mb-6">
          <label for="currency_code" class="block text-sm font-medium text-gray-700">Currency Code</label>
          <select
            v-model="form.currency_code"
            id="currency_code"
            required
            class="mt-1 block w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-blue-500 focus:border-blue-500"
          >
            <option value="USD">USD</option>
          </select>
        </div>

        <button
          type="submit"
          class="w-full sm:w-auto bg-blue-500 text-white py-2 px-4 rounded-md shadow-sm hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-opacity-50 mb-4"
        >
          Complete Profile
        </button>

        <button
          @click.prevent="$logout"
          class="w-full sm:w-auto ml-4 bg-red-500 text-white py-2 px-4 rounded-md shadow-sm hover:bg-red-600 focus:outline-none focus:ring-2 focus:ring-red-500 focus:ring-opacity-50"
        >
          Logout
        </button>
      </form>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  props: {
    isOpen: {
      type: Boolean,
      required: true,
    },
  },
  data() {
    return {
      form: {
        contact_number: "",
        house: "",
        street: "",
        area: "",
        zip_code: "",
        city: "",
        country: "",
        currency_code: "USD",
      },
      errors: {
        contact_number: "",
        zip_code: "",
      },
    };
  },
  methods: {
    validateContactNumber() {
      const sanitizedNumber = this.form.contact_number.replace(/\D/g, '');
      this.form.contact_number = sanitizedNumber;

      if (sanitizedNumber.length !== 13) {
        this.errors.contact_number = 'Contact number must be 13 digits long';
      } else {
        this.errors.contact_number = '';
      }
    },
    validateZipCode() {
      this.form.zip_code = this.form.zip_code.replace(/\D/g, '');

      if (this.form.zip_code.length < 4 || this.form.zip_code.length > 5) {
        this.errors.zip_code = 'Zip code must be between 4 and 5 digits.';
      } else {
        this.errors.zip_code = '';
      }
    },
    async submitProfile() {
      this.validateContactNumber();
      this.validateZipCode();

      if (this.errors.contact_number || this.errors.zip_code) {
        return;
      }

      try {
        const token = localStorage.getItem("vue-token");
        const userDetails = JSON.parse(localStorage.getItem("user-details"));
        const [firstName, ...lastNameParts] = userDetails.name.split(" ");
        const lastName = lastNameParts.join(" ");

        const requestData = {
          first_name: firstName,
          last_name: lastName,
          contact_number: this.form.contact_number,
          house: this.form.house,
          street: this.form.street,
          area: this.form.area,
          zip_code: parseInt(this.form.zip_code, 10),
          city: this.form.city,
          country: this.form.country,
          currency_code: this.form.currency_code,
        };

        const response = await axios.post(
          "http://localhost:8081/users/profiles",
          requestData,
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );

        if (response.status === 200) {
          this.$emit("profile-completed");
        }
      } catch (error) {
        console.error("Error creating user profile:", error);
      }
    },
  },
};
</script>
