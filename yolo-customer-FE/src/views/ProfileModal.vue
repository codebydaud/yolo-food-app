<template>
  <div v-if="isOpen" class="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50">
    <div class="bg-white rounded-lg shadow-lg p-8 w-full max-w-lg">
      <h2 class="text-2xl font-bold mb-6 text-center">Complete Your Profile</h2>
      <form @submit.prevent="submitProfile">
        <!-- Same form fields as before -->
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
        <!-- Other form fields... -->
        <button
          type="submit"
          class="w-full sm:w-auto bg-blue-500 text-white py-2 px-4 rounded-md shadow-sm hover:bg-blue-600 focus:outline-none focus:ring-2 focus:ring-blue-500 focus:ring-opacity-50 mb-4"
        >
          Complete Profile
        </button>
        <button
          @click.prevent="$emit('close')"
          class="w-full sm:w-auto ml-4 bg-red-500 text-white py-2 px-4 rounded-md shadow-sm hover:bg-red-600 focus:outline-none focus:ring-2 focus:ring-red-500 focus:ring-opacity-50"
        >
          Close
        </button>
      </form>
    </div>
  </div>
</template>

<script>
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
        contact_number: "1234567890123",
        house: "123",
        street: "Main St",
        area: "Downtown",
        zip_code: "12345",
        city: "Sample City",
        country: "Sample Country",
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
    submitProfile() {
      this.validateContactNumber();
      this.validateZipCode();

      if (this.errors.contact_number || this.errors.zip_code) {
        return;
      }

      this.$emit("profile-completed");
    },
  },
};
</script>
